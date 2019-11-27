package com.giul.projetonovo;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.giul.projetonovo.fragments.CalendarFragment;
import com.giul.projetonovo.fragments.PhotoFragment;
import com.giul.projetonovo.fragments.MapsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    SearchView searchView;
    //ImageButton twiter, googleMais;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.branco)));
        searchView = findViewById(R.id.search_view);



        //loading the default fragment

       loadFragment(new PhotoFragment());
        getSupportActionBar().setTitle("Photo");

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // setTheme(android.R.style.Theme_Black_NoTitleBar);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView msearchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        //msearchView.setBackgroundColor(android.R.color.black);
        EditText searchEditText = (EditText) msearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint("Search");
        searchEditText.setHintTextColor(getResources().getColor(R.color.preto));
        msearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        icon.setColorFilter(Color.BLACK);


        searchView= (SearchView) menu.findItem(R.id.search)
                .getActionView();
        searchView.setVisibility(View.VISIBLE);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }


        });




        return true;
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home:



                getSupportActionBar().setTitle("Photo");

                fragment = new PhotoFragment();
                break;

            case R.id.calendar:
                getSupportActionBar().setTitle("Calendar");
                fragment = new CalendarFragment();
                break;

            case R.id.maps:
                getSupportActionBar().setTitle("Maps");
                fragment = new MapsFragment();
                break;

//            case R.id.navigation_profile:
//                fragment = new ProfileFragment();
//                break;
        }

        return loadFragment(fragment);
    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void clickMensage(View view) {
        Toast.makeText(this, "Indisponivel no momento", Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            voltar();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void voltar() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }
}
