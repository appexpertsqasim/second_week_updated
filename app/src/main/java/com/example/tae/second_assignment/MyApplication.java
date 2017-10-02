package com.example.tae.second_assignment;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TAE Consultant on 23/09/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration= new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Log.i("test","Pass");
        Realm.setDefaultConfiguration(realmConfiguration);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i("test","Pass");
    }
}

