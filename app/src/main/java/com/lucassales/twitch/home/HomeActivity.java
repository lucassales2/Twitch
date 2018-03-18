package com.lucassales.twitch.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lucassales.twitch.R;
import com.lucassales.twitch.home.favorite.FavoriteGamesFragment;
import com.lucassales.twitch.home.top.TopGamesFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.lucassales.twitch.home.HomeActivityViewModel.NavigationItem.FAVORITE;
import static com.lucassales.twitch.home.HomeActivityViewModel.NavigationItem.TOP;

public class HomeActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeActivityViewModel viewModel;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeActivityViewModel.class);
        bindViews();
        bindViewModel();
    }

    private void bindViews() {
        navigationView = findViewById(R.id.home_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav_top:
                        viewModel.onNavigationItemClicked(TOP);
                        return true;
                    case R.id.home_nav_favorite:
                        viewModel.onNavigationItemClicked(FAVORITE);
                        return true;
                    default:
                        return false;
                }

            }
        });
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void bindViewModel() {
        viewModel.getNavigationEvent().observe(this, new Observer<HomeActivityViewModel.NavigationItem>() {
            @Override
            public void onChanged(@Nullable HomeActivityViewModel.NavigationItem navigationItem) {
                showNavigationItem(navigationItem);
            }
        });
    }

    private void showNavigationItem(HomeActivityViewModel.NavigationItem item) {
        if (item == null) {
            return;
        }

        int newItemId = 0;
        int position = -1;

        switch (item) {
            case TOP:
                newItemId = R.id.home_nav_top;
                position = 0;
                break;
            case FAVORITE:
                newItemId = R.id.home_nav_favorite;
                position = 1;
                break;
        }
        viewPager.setCurrentItem(position);
        if (navigationView.getSelectedItemId() != newItemId) {
            navigationView.getMenu().findItem(newItemId).setChecked(true);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return TopGamesFragment.newInstance();
                default: return FavoriteGamesFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
