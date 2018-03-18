package com.lucassales.twitch.home;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucassales.twitch.BR;
import com.lucassales.twitch.R;
import com.lucassales.twitch.data.database.entity.GameEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucassales on 16/03/2018.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private WeakReference<Callback> callbackWeakReference;
    private List<GameEntity> list = new ArrayList<>();

    public GameAdapter(Callback callback) {
        callbackWeakReference = new WeakReference<>(callback);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_holder_game_row,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GameEntity entity = list.get(position);
        holder.binding.setVariable(BR.game, entity);
        holder.binding.setVariable(BR.onGameClick, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callbackWeakReference.get() != null) {
                    callbackWeakReference.get().onGameClick(entity);
                }
            }
        });
        holder.binding.setVariable(BR.onFavoriteClick, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callbackWeakReference.get() != null) {
                    callbackWeakReference.get().onFavoriteClick(entity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(List<GameEntity> gameEntities) {
        list = gameEntities;
        notifyDataSetChanged();
    }

    public interface Callback {
        void onGameClick(GameEntity gameEntity);

        void onFavoriteClick(GameEntity gameEntity);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
