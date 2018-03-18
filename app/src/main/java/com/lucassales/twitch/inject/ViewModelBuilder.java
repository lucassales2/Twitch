package com.lucassales.twitch.inject;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

/**
 * Created by lucassales on 14/03/2018.
 */

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DaggerViewModelFactory factory);

}
