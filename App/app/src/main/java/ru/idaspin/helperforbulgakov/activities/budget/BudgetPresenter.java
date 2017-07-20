package ru.idaspin.helperforbulgakov.activities.budget;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 4:23 AM
 */

class BudgetPresenter {

    private final BudgetView mView;
    private int mPage = 0;

    BudgetPresenter(@NonNull BudgetView view) {
        mView = view;
    }

    void init() {
        mView.setupBudgetCount(0.0f);
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
                .getNote(mPage)
                .subscribe(notes -> {
                    if (notes.size() != 0) {
                        if (mPage == 0) {
                            mView.changeDataset(notes);
                        } else {
                            mView.attachDataset(notes);
                        }
                        if (notes.size() < 10) {
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

    void onAddButtonClick(String s, boolean i) {
        float p;
        try {
            p = Float.parseFloat(s);
            if (i) {
                p *= -1;
            }
            mView.setupBudgetCount(p);
        } catch (Exception ignored) {
            mView.showToastMessage(R.string.budget_dialog_invalid_count);
        }
    }

    void onLoadMoreButtonClick() {
        mPage++;
        makeRequest();
    }

    void onItemClick(Note v) {
        mView.showDeleteDialog(v.getId());
    }

    void onRemovingNoteSubmit(int v) {
        RepositoryProvider.provideRisensRepository()
                .removeNote(v)
                .subscribe(o -> {
                    mView.showToastMessage(R.string.budget_dialog_removed);
                    makeRequest();
                }, throwable -> {
                    mView.showToastMessage(R.string.budget_dialog_removed);
                    makeRequest();
                    // do nothing
                });
    }

    void onFabClick() {
        mView.showAddNoteDialog();
    }

    void onAddNoteSubmit(String s, String s1, String s2) {
        RepositoryProvider.provideRisensRepository()
                .addNote(s, s1, new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()), s2)
                .subscribe(o -> {
                    mView.clearRecycler();
                    mPage = 0;
                    makeRequest();
                    mView.showLoadMore();
                }, throwable -> {
                    // do nothing
                });
    }

    void onSwipeRefresh() {
        mView.clearRecycler();
        mPage = 0;
        makeRequest();
        mView.showLoadMore();
    }
}