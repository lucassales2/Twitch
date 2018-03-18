package com.lucassales.twitch.inject;

import com.lucassales.twitch.TwitchApplication;
import com.lucassales.twitch.data.database.DatabaseModule;
import com.lucassales.twitch.data.network.NetworkModule;
import com.lucassales.twitch.details.DetailsBuilder;
import com.lucassales.twitch.home.HomeBuilder;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by lucassales on 14/03/2018.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        HomeBuilder.class,
        DetailsBuilder.class,
        NetworkModule.class,
        DatabaseModule.class,
        AndroidSupportInjectionModule.class,
        ViewModelBuilder.class
})
public interface AppComponent extends AndroidInjector<TwitchApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TwitchApplication> {
        public abstract AppComponent build();
    }
}
