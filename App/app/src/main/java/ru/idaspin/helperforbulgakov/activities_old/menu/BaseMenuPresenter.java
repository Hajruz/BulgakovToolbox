package ru.idaspin.helperforbulgakov.activities_old.menu;

import android.os.Handler;
import android.support.annotation.NonNull;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities_old.books.BooksListActivity;
import ru.idaspin.helperforbulgakov.activities_old.budget.BudgetActivity;
import ru.idaspin.helperforbulgakov.activities_old.recorder.RecorderActivity;

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
                    mBaseView.openActivity(BooksListActivity.class);
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
        if (mLocalActivityName.equals("activities.books.BooksListActivity")) { // BooksActivity
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

    void onResume() {
        switch (mLocalActivityName) {
            case "activities.budget.LibraryActivity":
                mCurr = R.id.nav_budget;
                break;
            case "activities.books.BooksActivity":
                mCurr = R.id.nav_books;
                break;
            case "activities.recorder.RecorderActivity":
                mCurr = R.id.nav_recorder;
                break;
        }
        mBaseView.setMenuIconSelected(mCurr);
    }
}
