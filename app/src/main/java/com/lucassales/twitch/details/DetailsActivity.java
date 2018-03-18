package com.lucassales.twitch.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lucassales.twitch.BR;
import com.lucassales.twitch.R;
import com.lucassales.twitch.data.database.entity.GameEntity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailsActivity extends DaggerAppCompatActivity {

    private static final String PARAM_ID = "paramId";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ViewDataBinding viewDataBinding;
    private DetailsViewModel viewModel;

    public static Intent create(Context context, long gameId) {
        return new Intent(context, DetailsActivity.class)
                .putExtra(PARAM_ID, gameId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DetailsViewModel.class);
        long id = getIntent().getLongExtra(PARAM_ID, -1L);
        if (id == -1L) {
            finish();
        } else {
            viewModel.setGameId(id);
        }
        viewModel.getGameEntityLiveData().observe(this, new Observer<GameEntity>() {
            @Override
            public void onChanged(@Nullable GameEntity gameEntity) {
                viewDataBinding.setVariable(BR.game, gameEntity);
            }
        });

    }


}
