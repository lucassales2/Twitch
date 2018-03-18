package com.lucassales.twitch.home;

import android.content.Context;

import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.details.DetailsActivity;

import javax.inject.Inject;

/**
 * Created by Lucas Sales on 3/18/2018.
 */

public class HomeNavigator {
    private Context context;

    @Inject
    HomeNavigator(Context context) {
        this.context = context;
    }

    public void showDetails(GameEntity gameEntity) {
        context.startActivity(DetailsActivity.create(context, gameEntity.getId()));
    }
}
