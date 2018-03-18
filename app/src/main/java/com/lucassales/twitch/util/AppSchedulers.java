package com.lucassales.twitch.util;

import io.reactivex.Scheduler;

/**
 * Created by lucassales on 14/03/2018.
 */

public class AppSchedulers {
    private Scheduler network;
    private Scheduler main;
    private Scheduler database;

    public AppSchedulers(Scheduler network, Scheduler main, Scheduler database) {
        this.network = network;
        this.main = main;
        this.database = database;
    }

    public Scheduler network() {
        return network;
    }

    public Scheduler main() {
        return main;
    }

    public Scheduler database() {
        return database;
    }


}
