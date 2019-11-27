package com.giul.projetonovo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.giul.projetonovo.R;

public class CalendarioActivity extends AppCompatActivity {

   private TextView textMessage;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        textMessage = (TextView) findViewById(R.id.message);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {

           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                       //textMessage.setText(R.string.title_home);
                        startActivity(new Intent(getApplicationContext(),CalendarioActivity.class));
                        Toast.makeText(CalendarioActivity.this, "Photo", Toast.LENGTH_SHORT).show();
                        return true;
                   case R.id.calendar:
                       // textMessage.setText(R.string.title_dashboard);
                        startActivity(new Intent(getApplicationContext(),CalendarioActivity.class));
                        Toast.makeText(CalendarioActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
                       return true;
                    case R.id.maps:
                       // textMessage.setText(R.string.title_notifications);
                       Toast.makeText(CalendarioActivity.this, "Mapa", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }


}
