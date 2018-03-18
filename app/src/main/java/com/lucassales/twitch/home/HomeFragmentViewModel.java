package com.lucassales.twitch.home;

import com.lucassales.twitch.base.BaseViewModel;
import com.lucassales.twitch.data.database.dao.GameDao;
import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.util.AppSchedulers;

/**
 * Created by Lucas Sales on 3/18/2018.
 */

public class HomeFragmentViewModel extends BaseViewModel {
    protected final AppSchedulers appSchedulers;
    protected final GameDao gameDao;
    private HomeNavigator homeNavigator;

    public HomeFragmentViewModel(AppSchedulers appSchedulers, GameDao gameDao, HomeNavigator homeNavigator) {
        this.appSchedulers = appSchedulers;
        this.gameDao = gameDao;
        this.homeNavigator = homeNavigator;
    }

    public void onGameClick(GameEntity gameEntity) {
        homeNavigator.showDetails(gameEntity);
    }

    public void onFavoriteClick(final GameEntity gameEntity) {
        gameEntity.setFavorite(!gameEntity.isFavorite());
        appSchedulers.database().createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                gameDao.update(gameEntity);
            }
        });

    }
}
