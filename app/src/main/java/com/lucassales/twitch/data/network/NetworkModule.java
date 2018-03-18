package com.lucassales.twitch.data.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucassales.twitch.data.network.service.GameRetrofitService;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucassales on 14/03/2018.
 */
@Module
public class NetworkModule {

    private static final String CLIENT_ID = "client_id";

    @Provides
    OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    Interceptor provideInterceptor(@Named(CLIENT_ID) final String clientId) {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter(CLIENT_ID, clientId)
                        .build();
                Request newRequest = request.newBuilder()
                        .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                        .url(url)
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    @Named(CLIENT_ID)
    @Provides
    String provideClientId() {
        return "";
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.twitch.tv/kraken/")
                .build();
    }

    @Singleton
    @Provides
    GameRetrofitService provideGameRetrofitService(Retrofit retrofit) {
        return retrofit.create(GameRetrofitService.class);
    }
}
