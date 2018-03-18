package com.lucassales.twitch.home;

import android.arch.lifecycle.LiveData;

import com.lucassales.twitch.base.BaseViewModel;
import com.lucassales.twitch.util.SingleLiveEvent;

import javax.inject.Inject;

/**
 * Created by lucassales on 14/03/2018.
 */

public class HomeActivityViewModel extends BaseViewModel {

    private SingleLiveEvent<NavigationItem> navigationEvent = new SingleLiveEvent<>();

    @Inject
    HomeActivityViewModel() {
        navigationEvent.setValue(NavigationItem.TOP);
    }

    public void onNavigationItemClicked(NavigationItem item) {
        navigationEvent.setValue(item);
    }

    public LiveData<NavigationItem> getNavigationEvent() {
        return navigationEvent;
    }

    enum NavigationItem {
        TOP, FAVORITE
    }
}
