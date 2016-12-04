package com.example.jaime.finnica;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by checho on 21/11/2016.
 */
public class SugarOrmApp extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
