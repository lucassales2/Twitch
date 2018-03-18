
package com.lucassales.twitch.data.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top {

    @SerializedName("channels")
    @Expose
    private long channels;
    @SerializedName("viewers")
    @Expose
    private long viewers;
    @SerializedName("game")
    @Expose
    private Game game;

    public long getChannels() {
        return channels;
    }

    public void setChannels(long channels) {
        this.channels = channels;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
