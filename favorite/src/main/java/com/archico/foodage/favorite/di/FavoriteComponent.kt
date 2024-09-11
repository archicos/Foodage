package com.archico.foodage.favorite.di

import android.content.Context
import com.archico.foodage.di.FavoriteModuleDependencies
import com.archico.foodage.favorite.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependencies::class]
)

interface FavoriteComponent {

    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
