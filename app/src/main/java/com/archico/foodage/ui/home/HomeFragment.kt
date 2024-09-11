package com.archico.foodage.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.archico.core.data.Resource
import com.archico.core.domain.model.Recipe
import com.archico.core.ui.RecipeAdapter
import com.archico.foodage.R
import com.archico.foodage.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFavorite.layoutManager = layoutManager
        binding.rvFavorite.setHasFixedSize(true)

        homeViewModel.recipe.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> binding.pbProgress.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.pbProgress.visibility = View.GONE
                    val data = resource.data
                    if (data == null) {
                        return@observe
                    } else {
                        setData(data)
                    }
                }
                is Resource.Error -> {
                    binding.pbProgress.visibility = View.GONE
                    binding.msgError.root.visibility = View.VISIBLE
                    binding.msgError.tvErrorText.text =
                        resource.message ?: getString(R.string.something_wrong)
                }
            }
        }
    }

    private fun setData(recipes: List<Recipe>) {
        val adapter = RecipeAdapter(recipes)
        adapter.setOnItemClickCallback(object : RecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Recipe) {
                val action = HomeFragmentDirections.actionNavigationHomeToDetailActivity(
                    data.recipeId
                )
                findNavController().navigate(action)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}