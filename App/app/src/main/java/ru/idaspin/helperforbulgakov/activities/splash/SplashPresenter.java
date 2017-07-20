package ru.idaspin.helperforbulgakov.activities.splash;

import android.support.annotation.NonNull;

/**
 * Created by idaspin.
 * Date: 7/6/2017
 * Time: ~8:09 PM
 */

class SplashPresenter {

    private final SplashView mSplashView;

    SplashPresenter(@NonNull SplashView splashView) {
        mSplashView = splashView;
    }

    void init() {
        mSplashView.setupToolbar();
        mSplashView.setupAnimation();
    }

    void onEventFinish() {
        mSplashView.showApp();
    }
}