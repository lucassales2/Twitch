package com.lucassales.twitch.home.top;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.lucassales.twitch.data.database.dao.GameDao;
import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.data.network.dto.Top;
import com.lucassales.twitch.data.network.dto.TopGamesResponse;
import com.lucassales.twitch.data.network.service.GameRetrofitService;
import com.lucassales.twitch.home.HomeFragmentViewModel;
import com.lucassales.twitch.home.HomeNavigator;
import com.lucassales.twitch.util.AppSchedulers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import timber.log.Timber;

/**
 * Created by lucassales on 14/03/2018.
 */

public class TopGamesViewModel extends HomeFragmentViewModel {
    private final GameRetrofitService gameRetrofitService;
    private MutableLiveData<List<GameEntity>> liveData = new MutableLiveData<>();
    private boolean isSearching = false;
    private List<GameEntity> gameEntities;


    @Inject
    TopGamesViewModel(AppSchedulers appSchedulers, GameDao gameDao, HomeNavigator homeNavigator, GameRetrofitService gameRetrofitService) {
        super(appSchedulers, gameDao, homeNavigator);
        this.gameRetrofitService = gameRetrofitService;
        if (liveData.getValue() == null)
            refresh();
        disposables.add(gameDao.getAll()
                .subscribeOn(appSchedulers.database())
                .observeOn(appSchedulers.main())
                .filter(checkSearching())
                .subscribe(postValueConsumer(), onErrorConsumer()));
    }

    @NonNull
    private Predicate<List<GameEntity>> checkSearching() {
        return new Predicate<List<GameEntity>>() {
            @Override
            public boolean test(List<GameEntity> gameEntities) throws Exception {
                return !isSearching;
            }
        };
    }

    public LiveData<List<GameEntity>> getGameLiveData() {
        return liveData;
    }

    public void refresh() {
        disposables.add(loadData(20, 0)
                .doOnSuccess(new Consumer<List<GameEntity>>() {
                    @Override
                    public void accept(List<GameEntity> gameEntities) throws Exception {
                        gameDao.deleteNonFavorites();
                    }
                })
                .doOnSuccess(saveGameEntitiesConsumer())
                .subscribe(onSuccess(), onErrorConsumer()));
    }

    private Consumer<? super List<GameEntity>> onSuccess() {
        return new Consumer<List<GameEntity>>() {
            @Override
            public void accept(List<GameEntity> gameEntities) throws Exception {
                gameDao.bulkInsert(gameEntities);
            }
        };
    }

    @NonNull
    private Consumer<List<GameEntity>> postValueConsumer() {
        return new Consumer<List<GameEntity>>() {
            @Override
            public void accept(List<GameEntity> gameEntities) throws Exception {
                liveData.postValue(gameEntities);
            }
        };
    }

    @NonNull
    private Consumer<List<GameEntity>> saveGameEntitiesConsumer() {
        return new Consumer<List<GameEntity>>() {
            @Override
            public void accept(List<GameEntity> gameEntities) throws Exception {
                TopGamesViewModel.this.gameEntities = gameEntities;
            }
        };
    }

    public void loadMore(int offset) {

        if (!isSearching)
            disposables.add(loadData(20, offset)
                    .map(new Function<List<GameEntity>, List<GameEntity>>() {
                        @Override
                        public List<GameEntity> apply(List<GameEntity> gameEntities) throws Exception {
                            List<GameEntity> value = liveData.getValue();
                            if (value != null) {
                                value.addAll(gameEntities);
                            }
                            return value;
                        }
                    })
                    .doOnSuccess(saveGameEntitiesConsumer())
                    .filter(checkSearching())
                    .subscribe(onSuccess(), onErrorConsumer()));
    }

    @NonNull
    private Consumer<Throwable> onErrorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Timber.e(throwable);
            }
        };
    }

    private Single<List<GameEntity>> loadData(int limit, int offset) {
        return gameRetrofitService.getTopGames(limit, offset)
                .subscribeOn(appSchedulers.network())
                .map(new Function<TopGamesResponse, List<GameEntity>>() {
                    @Override
                    public List<GameEntity> apply(TopGamesResponse topGamesResponse) throws Exception {
                        ArrayList<GameEntity> list = new ArrayList<>();
                        List<Long> favoritesIds = gameDao.getFavoritesIds();
                        for (Top top : topGamesResponse.getTop()) {
                            list.add(new GameEntity(
                                    top.getGame().getId(),
                                    top.getGame().getName(),
                                    top.getGame().getBox().getMedium(),
                                    top.getGame().getBox().getLarge(), top.getViewers(),
                                    favoritesIds.contains(top.getGame().getId())));
                        }
                        return list;
                    }
                });
    }

    public void onQuery(String string) {
        if (gameEntities != null) {
            if (string.isEmpty()) {
                isSearching = false;
                liveData.postValue(gameEntities);
            } else {
                isSearching = true;
                List<GameEntity> temp = new ArrayList<>();
                String s = string.toLowerCase();
                for (GameEntity gameEntity : gameEntities) {
                    if (gameEntity.getName().toLowerCase().contains(s)) {
                        temp.add(gameEntity);
                    }
                }
                liveData.postValue(temp);
            }
        }
    }
}
