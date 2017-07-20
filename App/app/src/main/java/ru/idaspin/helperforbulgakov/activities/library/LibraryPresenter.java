package ru.idaspin.helperforbulgakov.activities.library;

import android.app.Activity;
import android.support.annotation.NonNull;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 3:09 AM
 */

class LibraryPresenter {

    private final LibraryView mView;
    private int mPage = 0;

    LibraryPresenter(@NonNull LibraryView view) {
        mView = view;
    }

    void init() {
        mView.setupRecycler();
        mView.setupFab();
        mView.setupSwipeRefresh();
    }

    void onResumeListenerForRequest() {
        mView.clearRecycler();
        mPage = 0;
        makeRequest();
        mView.showLoadMore();
    }

    private void makeRequest() {
        RepositoryProvider.provideRisensRepository()
                .getBooks(mPage)
                .subscribe(books -> {
                    if (books.size() != 0) {
                        if (mPage == 0) {
                            mView.changeDataset(books);
                        } else {
                            mView.attachDataset(books);
                        }
                        if (books.size() < 10) {
                            mView.hideLoadMore();
                        }
                    } else {
                        mPage--;
                        mView.hideLoadMore();
                    }
                    mView.hideSwipeRefreshLayout();
                }, throwable -> {
                    if (mPage != 0) {
                        mPage--;
                    }
                    mView.hideSwipeRefreshLayout();
                });
    }

    void onLoadMoreButtonClick() {
        mPage++;
        makeRequest();
    }

    void onFabClick() {
        mView.showBookCreatorWizard();
    }

    void onSwipeRefresh() {
        mView.clearRecycler();
        mPage = 0;
        makeRequest();
        mView.showLoadMore();
    }

    void onActivityResult(int resultCode) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                mView.clearRecycler();
                mPage = 0;
                makeRequest();
                mView.showLoadMore();
                break;
            default:
                mView.showToastMessage(R.string.chooser_error);
                break;
        }
    }
}