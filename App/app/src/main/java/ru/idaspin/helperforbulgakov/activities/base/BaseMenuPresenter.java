package ru.idaspin.helperforbulgakov.activities.base;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities.budget.BudgetActivity;
import ru.idaspin.helperforbulgakov.activities.library.LibraryActivity;
import ru.idaspin.helperforbulgakov.activities.recorder.RecorderActivity;

/**
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:18 PM
 */

class BaseMenuPresenter {

    private BaseMenuView mBaseView;
    private String mLocalActivityName;

    private boolean doubleBackToExitPressedOnce = false;
    private int mCurr;

    BaseMenuPresenter(@NonNull BaseMenuView mainView) {
        mBaseView = mainView;
    }

    void init(int childId, String localClassName) {
        mBaseView.setupContainerBase(childId);
        mBaseView.setupViewsBase();
        mLocalActivityName = localClassName;
    }

    boolean onNavigationItemSelected(int itemId) {
        switch (itemId) {
            case R.id.nav_books:
                if (itemId != mCurr) {
                    mBaseView.openActivity(LibraryActivity.class);
                }
                break;
            case R.id.nav_recorder:
                if (itemId != mCurr) {
                    mBaseView.openActivity(RecorderActivity.class);
                }
                break;
            case R.id.nav_budget:
                if (itemId != mCurr) {
                    mBaseView.openActivity(BudgetActivity.class);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    void onBackPressed() {
        if (mLocalActivityName.equals("screen.library.LibraryActivity")) {
            if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                mBaseView.showToastMessage(R.string.message_back);
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 3000);
            } else {
                mBaseView.closeApp();
            }
        } else {
            mBaseView.doBackPress();
        }
    }

    void onResume(long last) {
        if (last == 0 || TimeUnit.MILLISECONDS.toMinutes(new Date(System.currentTimeMillis()).getTime() - last) >= 10) {
            mBaseView.onResumeListenerForRequest();
        }
        switch (mLocalActivityName) {
            case "screen.budget.BudgetActivity":
                mCurr = R.id.nav_budget;
                break;
            case "screen.library.LibraryActivity":
                mCurr = R.id.nav_books;
                break;
            case "screen.recorder.RecorderActivity":
                mCurr = R.id.nav_recorder;
                break;
        }
        mBaseView.setMenuIconSelected(mCurr);
    }
}
