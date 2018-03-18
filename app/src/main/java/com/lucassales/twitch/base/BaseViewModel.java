package com.lucassales.twitch.base;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by lucassales on 14/03/2018.
 */

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
