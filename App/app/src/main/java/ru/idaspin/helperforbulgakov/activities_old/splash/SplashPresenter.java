package ru.idaspin.helperforbulgakov.activities_old.splash;

import android.support.annotation.NonNull;

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