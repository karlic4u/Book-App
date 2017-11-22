package esi.kaly.com.AndroidCodingChallenge;

import android.app.Application;

import esi.kaly.com.AndroidCodingChallenge.rest.RestController;

public class BookApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        RestController.initializeRestController();
    }
}
