package com.lucassales.twitch.home.top;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lucassales.twitch.util.EndlessRecyclerViewScrollListener;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by lucassales on 14/03/2018.
 */

public class TopGamesFragment extends DaggerFragment implements GameAdapter.Callback {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TopGamesViewModel viewModel;
    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private SwipeRefreshLayout swipeToRefresh;

    public static TopGamesFragment newInstance() {
        return new TopGamesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TopGamesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeToRefresh = rootView.findViewById(R.id.swipeToRefresh);
        EditText edit_query = rootView.findViewById(R.id.edit_query);
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.onQuery(editable.toString());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.spanCount));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                swipeToRefresh.setRefreshing(true);
                viewModel.loadMore(totalItemsCount);
            }
        });

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 viewModel.refresh();
            }
        });
        gameAdapter = new GameAdapter(this);
        recyclerView.setAdapter(gameAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getGameLiveData().observe(this, new Observer<List<GameEntity>>() {
            @Override
            public void onChanged(@Nullable List<GameEntity> gameEntities) {
                gameAdapter.refresh(gameEntities);
                swipeToRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeToRefresh.setRefreshing(false);
                    }
                });
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
