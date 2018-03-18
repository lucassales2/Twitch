package com.lucassales.twitch.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lucassales.twitch.data.database.entity.GameEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * Created by lucassales on 14/03/2018.
 */

@Dao
public interface GameDao {

    @Query("SELECT * FROM games WHERE favorite = 1 ORDER BY spectators DESC")
    public Flowable<List<GameEntity>> getFavorites();

    @Update
    int update(GameEntity gameEntity);

    @Query("SELECT id FROM games WHERE favorite = 1")
    List<Long> getFavoritesIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<GameEntity> gameEntities);

    @Query("SELECT * FROM games WHERE id =:id")
    Maybe<GameEntity> getGameById(long id);

    @Query("DELETE FROM games WHERE favorite = 0")
    int deleteNonFavorites();

    @Query("SELECT * FROM games ORDER BY spectators DESC")
    Flowable<List<GameEntity>> getAll();
}
