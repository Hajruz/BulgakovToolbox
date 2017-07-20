package ru.idaspin.helperforbulgakov.activities.library;

import java.util.List;

import ru.idaspin.helperforbulgakov.content.Book;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 3:09 AM
 */

interface LibraryView {

    void showToastMessage(int message);

    void setupRecycler();

    void hideLoadMore();

    void attachDataset(List<Book> notes);

    void setupFab();

    void showBookCreatorWizard();

    void clearRecycler();

    void changeDataset(List<Book> notes);

    void setupSwipeRefresh();

    void hideSwipeRefreshLayout();

    void showLoadMore();
}
