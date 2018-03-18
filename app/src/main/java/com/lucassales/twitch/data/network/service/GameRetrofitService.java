package com.lucassales.twitch.data.network.service;

import com.lucassales.twitch.data.network.dto.TopGamesResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lucassales on 14/03/2018.
 */

public interface GameRetrofitService {
    @GET("games/top")
    Single<TopGamesResponse> getTopGames(@Query("limit") int limit, @Query("offset") int offset);

}
