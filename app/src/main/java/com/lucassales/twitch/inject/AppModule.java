package com.lucassales.twitch.inject;

import android.content.Context;

import com.lucassales.twitch.TwitchApplication;
import com.lucassales.twitch.util.AppSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lucassales on 14/03/2018.
 */

@Module
public class AppModule {

    @Singleton
    @Provides
    AppSchedulers provideAppSchedulers() {
        return new AppSchedulers(Schedulers.io(), AndroidSchedulers.mainThread(), Schedulers.single());
    }

    @Provides
    Context provideContext(TwitchApplication twitchApplication) {
        return twitchApplication.getApplicationContext();
    }
}
