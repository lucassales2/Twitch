package com.lucassales.twitch.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Locale;

/**
 * Created by lucassales on 14/03/2018.
 */
@Entity(tableName = "games",
        indices = {
        @Index(value = "favorite")
})
public class GameEntity {
    @PrimaryKey
    private long id;
    private String name;
    private String logo;
    private long spectators;
    @ColumnInfo(name = "favorite")
    private boolean favorite;
    private String logoLarge;

    public GameEntity(long id, String name, String logo, String logoLarge, long spectators, boolean favorite) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.spectators = spectators;
        this.favorite = favorite;
        this.logoLarge = logoLarge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpectators() {
        return spectators;
    }

    @Ignore
    public String getSpectatorsString() {
        return String.format(Locale.getDefault(), "%,d", spectators);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLogoLarge() {
        return logoLarge;
    }
}
