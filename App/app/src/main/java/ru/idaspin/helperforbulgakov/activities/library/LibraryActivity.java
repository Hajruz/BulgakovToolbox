package ru.idaspin.helperforbulgakov.activities.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.activities.base.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.activities.chooser.ChooserActivity;
import ru.idaspin.helperforbulgakov.widgets.EmptyRecyclerView;

import static android.view.View.GONE;

/**
 * Updated by idaspin.
 * Date: 7/19/2017
 * Time: 3:09 AM
 * + Data binding
 * + MVP integrated
 * + Re:design
 * + EmptyRecyclerView functionality
 * + FilePicker transferred to ChooserActivity
 */

public class LibraryActivity extends BaseMenuActivity implements LibraryView {

    @BindView(R.id.library_recycler) EmptyRecyclerView mEmptyRecyclerView;
    @BindView(R.id.library_emptyview) View mEmptyView;
    @BindView(R.id.library_loadmore) View mLoadView;

    private LibraryPresenter mPresenter;
    private BooksAdapter mAdapter;

    @Override
    public void finish(){
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.content_library);
        ButterKnife.bind(this);

        mPresenter = new LibraryPresenter(this);
        mPresenter.init();
    }

    @Override
    public void onResumeListenerForRequest() {
        mPresenter.onResumeListenerForRequest();
    }

    @Override
    protected int setActivityTitle() {
        return R.string.title_books;
    }

    @Override
    public void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mEmptyRecyclerView.setLayoutManager(layoutManager);
        mEmptyRecyclerView.setEmptyView(mEmptyView);
        mEmptyRecyclerView.setLoadView(mLoadView);
        mEmptyRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new BooksAdapter();
        mAdapter.attachToRecyclerView(mEmptyRecyclerView);
    }

    @Override
    public void hideLoadMore() {
        mLoadView.setTag(false);
        mLoadView.setVisibility(GONE);
    }

    @Override
    public void changeDataset(List<Book> notes) {
        mAdapter.changeDataSet(notes);
    }

    @Override
    public void setupSwipeRefresh() {
        setupSwipeRefreshLayout(() -> mPresenter.onSwipeRefresh());
    }

    @Override
    public void showLoadMore() {
        mLoadView.setTag(true);
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void attachDataset(List<Book> notes) {
        mAdapter.attachDataSet(notes);
    }

    @Override
    public void setupFab() {
        setupFloatingActionButton(v -> mPresenter.onFabClick(), R.mipmap.ic_add_button);
    }

    @Override
    public void showBookCreatorWizard() {
        Intent intent = new Intent(this, ChooserActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        mPresenter.onActivityResult(resultCode);
    }

    @Override
    public void clearRecycler() {
        mAdapter.clear();
    }

    @OnClick(R.id.library_loadmore)
    public void onLoadMoreButtonClick() {
        mPresenter.onLoadMoreButtonClick();
    }
}
