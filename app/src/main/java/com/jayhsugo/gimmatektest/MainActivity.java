package com.jayhsugo.gimmatektest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TabLayout.Tab> tabList;
    private TabLayout tabLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActBarDrawerToggle;
    private Fragment currentFragment = null;
    private int[] tabSelectorIcons = {
            R.drawable.selector_tab1,
            R.drawable.selector_tab2,
            R.drawable.selector_tab3,
            R.drawable.selector_tab4,
            R.drawable.selector_tab5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        ActionBar actBar = getSupportActionBar();
        actBar.setElevation(0);
        actBar.setDisplayHomeAsUpEnabled(true);
        actBar.setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mActBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mActBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mActBarDrawerToggle);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(0);
        initBody(tabLayout.getSelectedTabPosition());
        setupTabIcons();
    }

    private void initEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initBody(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
            mActBarDrawerToggle.syncState();
    }

    public void initBody(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TestFragment();
                switchFragment(fragment);
                setTitle(R.string.tab_home);
                break;
            case 1:
                fragment = new NewsBoardFragment();
                switchFragment(fragment);
                setTitle(R.string.tab_newsboard);
                break;
            case 2:
                fragment = new TestFragment();
                switchFragment(fragment);
                setTitle(R.string.tab_search);
                break;
            case 3:
                fragment = new TestFragment();
                switchFragment(fragment);
                setTitle(R.string.tab_coincenter);
                break;
            case 4:
                fragment = new TestFragment();
                switchFragment(fragment);
                setTitle(R.string.tab_notification);
                break;
            default:
                break;
        }
    }

    private void setupTabIcons() {
        tabList = new ArrayList<>();
        for (int i = 0; i < tabSelectorIcons.length; i++) {
            tabList.add(tabLayout.newTab().setCustomView(getTabView(i)));
            tabLayout.addTab(tabList.get(i));
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
        ImageView tabItemView = view.findViewById(R.id.tabItemView);
        tabItemView.setImageResource(tabSelectorIcons[position]);
        return view;
    }

    public void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()時currentFragment為null，所以要判斷一下
            if(currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.content_frame, targetFragment);
            transaction.commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }
}
