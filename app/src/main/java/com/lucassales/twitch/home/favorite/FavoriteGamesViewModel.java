package com.lucassales.twitch.home.favorite;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lucassales.twitch.data.database.dao.GameDao;
import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.home.HomeFragmentViewModel;
import com.lucassales.twitch.home.HomeNavigator;
import com.lucassales.twitch.util.AppSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import timber.log.Timber;

/**
 * Created by lucassales on 14/03/2018.
 */

public class FavoriteGamesViewModel extends HomeFragmentViewModel {

    private MutableLiveData<List<GameEntity>> listMutableLiveData = new MutableLiveData<>();
    private boolean isSearching = false;

    @Inject
    FavoriteGamesViewModel(AppSchedulers appSchedulers, GameDao gameDao, HomeNavigator homeNavigator) {
        super(appSchedulers, gameDao, homeNavigator);
        disposables.add(gameDao.getFavorites()
                .subscribeOn(appSchedulers.database())
                .observeOn(appSchedulers.main())
                .filter(new Predicate<List<GameEntity>>() {
                    @Override
                    public boolean test(List<GameEntity> gameEntities) throws Exception {
                        return !isSearching;
                    }
                })
                .subscribe(new Consumer<List<GameEntity>>() {
                    @Override
                    public void accept(List<GameEntity> gameEntities) throws Exception {
                        listMutableLiveData.postValue(gameEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));
    }

    public LiveData<List<GameEntity>> getFavoriteLiveData() {
        return listMutableLiveData;
    }


}
