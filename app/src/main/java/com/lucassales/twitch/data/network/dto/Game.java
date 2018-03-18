
package com.lucassales.twitch.data.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("_id")
    @Expose
    private long id;
    @SerializedName("box")
    @Expose
    private Box box;
    @SerializedName("giantbomb_id")
    @Expose
    private long giantbombId;
    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private long popularity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public long getGiantbombId() {
        return giantbombId;
    }

    public void setGiantbombId(long giantbombId) {
        this.giantbombId = giantbombId;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

}
