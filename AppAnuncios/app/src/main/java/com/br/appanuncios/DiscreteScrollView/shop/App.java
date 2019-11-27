package com.br.appanuncios.DiscreteScrollView.shop;

import android.app.Application;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        com.br.appanuncios.DiscreteScrollView.shop.DiscreteScrollViewOptions.init(this);
    }
}
