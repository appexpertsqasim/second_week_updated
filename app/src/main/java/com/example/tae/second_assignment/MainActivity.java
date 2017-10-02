package com.example.tae.second_assignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.container,new RockMusicActivity())
                            .commit();
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction().replace(R.id.container,new PopFragment())
                            .commit();
                case R.id.navigation_notifications:
                    fragmentManager.beginTransaction().replace(R.id.container,new ClassicMusicActivity() )
                            .commit();
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_main);
        if(savedInstanceState ==null){
            fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.container,new RockMusicActivity())
                    .addToBackStack(null)
                    .commit();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
