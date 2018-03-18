package com.lucassales.twitch.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.lucassales.twitch.base.BaseViewModel;
import com.lucassales.twitch.data.database.dao.GameDao;
import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.util.AppSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by lucassales on 15/03/2018.
 */

public class DetailsViewModel extends BaseViewModel {

    private final AppSchedulers appSchedulers;
    private final GameDao gameDao;
    private MutableLiveData<GameEntity> mutableLiveData = new MutableLiveData<>();
    private long id;

    @Inject
    DetailsViewModel(AppSchedulers appSchedulers, GameDao gameDao) {
        this.appSchedulers = appSchedulers;
        this.gameDao = gameDao;
    }

    public void setGameId(long id) {
        if (id != this.id) {
            this.id = id;
            setUpLiveData();
        }
    }

    public LiveData<GameEntity> getGameEntityLiveData() {
        return mutableLiveData;
    }


    private void setUpLiveData() {
        disposables.add(gameDao.getGameById(id)
                .subscribeOn(appSchedulers.database())
                .observeOn(appSchedulers.main())
                .subscribe(new Consumer<GameEntity>() {
                    @Override
                    public void accept(GameEntity gameEntity) throws Exception {
                        mutableLiveData.postValue(gameEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                    }
                }));
    }


}
