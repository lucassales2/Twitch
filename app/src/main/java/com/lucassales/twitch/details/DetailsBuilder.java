package com.lucassales.twitch.details;

import android.arch.lifecycle.ViewModel;

import com.lucassales.twitch.inject.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by lucassales on 15/03/2018.
 */

@Module
public abstract class DetailsBuilder {
    @ContributesAndroidInjector
    abstract DetailsActivity detailsActivity();

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    public abstract ViewModel bindDetailsViewModel(DetailsViewModel detailsViewModel);
}
