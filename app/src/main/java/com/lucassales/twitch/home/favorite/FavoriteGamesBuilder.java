package com.lucassales.twitch.home.favorite;

import android.arch.lifecycle.ViewModel;

import com.lucassales.twitch.inject.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by lucassales on 14/03/2018.
 */

@Module
public abstract class FavoriteGamesBuilder {
    @ContributesAndroidInjector
    abstract FavoriteGamesFragment favoriteGamesFragment();

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteGamesViewModel.class)
    public abstract ViewModel bindFavoriteGamesViewModel(FavoriteGamesViewModel viewModel);
}
