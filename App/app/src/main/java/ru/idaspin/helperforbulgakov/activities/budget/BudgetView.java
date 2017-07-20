package ru.idaspin.helperforbulgakov.activities.budget;

import java.util.List;

import ru.idaspin.helperforbulgakov.content.Note;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 4:23 AM
 */

interface BudgetView {

    void setupBudgetCount(float p);

    void showToastMessage(int message);

    void setupRecycler();

    void hideLoadMore();

    void attachDataset(List<Note> notes);

    void showDeleteDialog(int v);

    void setupFab();

    void showAddNoteDialog();

    void clearRecycler();

    void changeDataset(List<Note> notes);

    void setupSwipeRefresh();

    void hideSwipeRefreshLayout();

    void showLoadMore();
}
