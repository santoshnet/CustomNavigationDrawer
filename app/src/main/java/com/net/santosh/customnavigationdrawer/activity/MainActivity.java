package com.net.santosh.customnavigationdrawer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.net.santosh.customnavigationdrawer.R;
import com.net.santosh.customnavigationdrawer.adapter.DrawerItemCustomAdapter;
import com.net.santosh.customnavigationdrawer.fragment.GalleryFragment;
import com.net.santosh.customnavigationdrawer.fragment.HomeFragment;
import com.net.santosh.customnavigationdrawer.fragment.PhotoFragment;
import com.net.santosh.customnavigationdrawer.model.NavDrawerModel;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        NavDrawerModel[] drawerItem = new NavDrawerModel[6];

        drawerItem[0] = new NavDrawerModel(R.drawable.home, "Home", "Default Page");
        drawerItem[1] = new NavDrawerModel(R.drawable.photo, "Photo", "Photo Section");
        drawerItem[2] = new NavDrawerModel(R.drawable.gallery, "Gallery", "All Images Section");
        drawerItem[3] = new NavDrawerModel(R.drawable.about_us, "AboutUs", "About Company");
        drawerItem[4] = new NavDrawerModel(R.drawable.help, "Help", "User Guide");
        drawerItem[5] = new NavDrawerModel(R.drawable.bottomsheet, "Bottom Sheet", "Show Bottom Sheet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        if (savedInstanceState == null) {
            selectItem(0);
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = new HomeFragment();
                                break;
                            case R.id.action_item2:
                                selectedFragment = new PhotoFragment();
                                break;
                            case R.id.action_item3:
                                selectedFragment = new GalleryFragment();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HomeFragment());
        transaction.commit();



    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();

                break;
            case 1:
                fragment = new PhotoFragment();
                break;
            case 2:
                fragment = new GalleryFragment();
                //startActivity(new Intent(getApplicationContext(), Register2Activity.class));
                break;
            case 3:

                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;
            case 4:
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                break;
            case 5:
                startActivity(new Intent(getApplicationContext(), BottomSheetActivity.class));
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

}
