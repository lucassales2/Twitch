package com.lucassales.twitch.home;

import android.arch.lifecycle.ViewModel;

import com.lucassales.twitch.home.favorite.FavoriteGamesBuilder;
import com.lucassales.twitch.home.top.TopGamesBuilder;
import com.lucassales.twitch.inject.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by lucassales on 14/03/2018.
 */
@Module(includes = {
        TopGamesBuilder.class,
        FavoriteGamesBuilder.class
})

public abstract class HomeBuilder {
    @ContributesAndroidInjector(modules = {
            HomeModule.class
    })
    abstract HomeActivity homeActivity();

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel.class)
    public abstract ViewModel bindHomeActivityViewModel(HomeActivityViewModel homeActivityViewModel);

}
