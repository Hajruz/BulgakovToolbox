package ru.idaspin.helperforbulgakov.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities.library.LibraryActivity;

/**
 * Created by idaspin.
 * Date: 7/6/2017
 * Time: ~8:09 PM
 */

public class SplashActivity extends AppCompatActivity implements SplashView, Animation.AnimationListener, View.OnClickListener {

    @BindView(R.id.splash_image) ImageView mImageView;
    @BindView(R.id.splash_text)  TextView  mTextView;

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mPresenter = new SplashPresenter(this);
        mPresenter.init();
    }

    @Override
    public void showApp() {
        startActivity(new Intent(this, LibraryActivity.class));
        this.finish();
    }

    @Override
    public void setupAnimation() {
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mImageView.startAnimation(myFadeInAnimation);
        mImageView.setOnClickListener(this);
        mTextView.startAnimation(myFadeInAnimation);
        mTextView.setOnClickListener(this);
        myFadeInAnimation.setAnimationListener(this);
    }

    @Override
    public void setupToolbar() {
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mPresenter.onEventFinish();
    }

    @Override
    public void onClick(View v) {
        mPresenter.onEventFinish();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // do nothing
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // do nothing
    }
}
