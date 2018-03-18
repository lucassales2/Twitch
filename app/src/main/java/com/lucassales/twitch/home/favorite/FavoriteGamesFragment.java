package com.lucassales.twitch.home.favorite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lucassales.twitch.R;
import com.lucassales.twitch.data.database.entity.GameEntity;
import com.lucassales.twitch.home.GameAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by lucassales on 14/03/2018.
 */

public class FavoriteGamesFragment extends DaggerFragment implements GameAdapter.Callback {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoriteGamesViewModel viewModel;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;


    public static FavoriteGamesFragment newInstance() {
        return new FavoriteGamesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FavoriteGamesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(gameAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getFavoriteLiveData().observe(this, new Observer<List<GameEntity>>() {
            @Override
            public void onChanged(@Nullable List<GameEntity> gameEntities) {
                gameAdapter.refresh(gameEntities);
            }
        });
    }

    @Override
    public void onGameClick(GameEntity gameEntity) {
        viewModel.onGameClick(gameEntity);
    }

    @Override
    public void onFavoriteClick(GameEntity gameEntity) {
        viewModel.onFavoriteClick(gameEntity);
    }
}
