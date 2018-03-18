
package com.lucassales.twitch.data.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopGamesResponse {

    @SerializedName("_total")
    @Expose
    private long total;
    @SerializedName("top")
    @Expose
    private List<Top> top = null;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }

}
