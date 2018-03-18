package com.lucassales.twitch.home.top;

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
public abstract class TopGamesBuilder {
    @ContributesAndroidInjector
    abstract TopGamesFragment topGamesFragment();

    @Binds
    @IntoMap
    @ViewModelKey(TopGamesViewModel.class)
    public abstract ViewModel bindTopGamesViewModel(TopGamesViewModel viewModel);
}
