package com.lucassales.twitch.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.lucassales.twitch.data.database.dao.GameDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucassales on 14/03/2018.
 */

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    TwitchDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, TwitchDatabase.class, "lucassales.twitch")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    GameDao provideGameDao(TwitchDatabase database) {
        return database.gameDao();
    }

}
