package ru.idaspin.helperforbulgakov.activities.base;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:18 PM
 */

interface BaseMenuView {

    void setupContainerBase(int childId);

    void setupViewsBase();

    void openActivity(Class<? extends BaseMenuActivity> activity);

    void doBackPress();

    void showToastMessage(int message_back);

    void showToastMessage(String message_back);

    void closeApp();

    void setMenuIconSelected(int i);

    void onResumeListenerForRequest();

    void setupSwipeRefreshLayout(SwipeRefreshLayout.OnRefreshListener listener);

    void hideSwipeRefreshLayout();
}
