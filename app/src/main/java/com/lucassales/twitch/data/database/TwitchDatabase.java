package com.lucassales.twitch.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lucassales.twitch.data.database.dao.GameDao;
import com.lucassales.twitch.data.database.entity.GameEntity;

/**
 * Created by lucassales on 14/03/2018.
 */
@Database(entities = {
        GameEntity.class
},
        version = 2,
        exportSchema = false)
public abstract class TwitchDatabase extends RoomDatabase {
    abstract GameDao gameDao();
}
