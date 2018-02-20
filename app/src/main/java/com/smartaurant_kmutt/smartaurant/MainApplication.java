package com.smartaurant_kmutt.smartaurant;

import android.app.Application;
import android.content.Intent;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by LB on 19/2/2561.
 */

public class MainApplication extends Application{
    public static Intent intent;
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
