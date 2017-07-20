package ru.idaspin.helperforbulgakov.activities.budget;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Note;
import ru.idaspin.helperforbulgakov.activities.base.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.utils.Constants;
import ru.idaspin.helperforbulgakov.utils.SharedPrefsUtil;
import ru.idaspin.helperforbulgakov.widgets.EmptyRecyclerView;

import static android.view.View.GONE;

/**
 * Updated by idaspin.
 * Date: 7/19/2017
 * Time: 4:23 AM
 * + Data binding
 * + MVP integrated
 * + Re:design
 * + EmptyRecyclerView additional functionality
 */

public class BudgetActivity extends BaseMenuActivity implements BudgetView {

    @BindView(R.id.budget_count)     TextView mTextViewCount;
    @BindView(R.id.budget_editor)    EditText mEditText;
    @BindView(R.id.budget_recycler)  EmptyRecyclerView mEmptyRecyclerView;
    @BindView(R.id.budget_emptyview) View mEmptyView;
    @BindView(R.id.budget_loadmore)  View mLoadView;

    private BudgetPresenter mPresenter;
    private NotesAdapter    mAdapter;
    private AlertDialog     mAlert;

    @Override
    public void finish(){
        if (mAlert != null) {
            mAlert.dismiss();
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.content_budget);
        ButterKnife.bind(this);

        mPresenter = new BudgetPresenter(this);
        mPresenter.init();
    }

    @Override
    public void onResumeListenerForRequest() {
        mPresenter.onResumeListenerForRequest();
    }

    @Override
    protected int setActivityTitle() {
        return R.string.title_budget;
    }

    @Override
    public void setupBudgetCount(float p) {
        float f = 0.0f;
        if (p != 0.0f) {
            f += p;
            SharedPrefsUtil.setFloatPreference(this, Constants.SHARED_BUDGET_COUNT, f);
        } else {
            f = SharedPrefsUtil.getFloatPreference(this, Constants.SHARED_BUDGET_COUNT, 0.0f);
        }
        mTextViewCount.setText(String.format("%s %s", f, getString(R.string.budget_val)));
    }

    @Override
    public void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mEmptyRecyclerView.setLayoutManager(layoutManager);
        mEmptyRecyclerView.setEmptyView(mEmptyView);
        mEmptyRecyclerView.setLoadView(mLoadView);
        mEmptyRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new NotesAdapter();
        mAdapter.attachToRecyclerView(mEmptyRecyclerView);
        mAdapter.setOnItemClickListener((v) -> mPresenter.onItemClick(v));
    }

    @Override
    public void hideLoadMore() {
        mLoadView.setTag(false);
        mLoadView.setVisibility(GONE);
    }

    @Override
    public void changeDataset(List<Note> notes) {
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
    public void attachDataset(List<Note> notes) {
        mAdapter.attachDataSet(notes);
    }

    @Override
    public void showDeleteDialog(int v) {
        mAlert = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_budget_remove)
                .setMessage(R.string.dialog_budget_remove_message)
                .setPositiveButton(getResources().getString(R.string.dialog_yes), (dialog, which) -> {
                    mPresenter.onRemovingNoteSubmit(v);
                    dialog.cancel();
                })
                .setNegativeButton(getResources().getString(R.string.dialog_cansel), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void setupFab() {
        setupFloatingActionButton(v -> mPresenter.onFabClick(), R.mipmap.ic_add_button);
    }

    @Override
    public void showAddNoteDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View dialogView = factory.inflate(R.layout.dialog_budget_add, null);
        mAlert = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_budget_add)
                .setView(dialogView)
                .setMessage(R.string.dialog_budget_add_message)
                .setPositiveButton(getResources().getString(R.string.dialog_yes), (dialog, which) -> {
                    EditText e1 = (EditText) dialogView.findViewById(R.id.note_top_edit_text);
                    EditText e2 = (EditText) dialogView.findViewById(R.id.note_text_edit_text);
                    EditText e3 = (EditText) dialogView.findViewById(R.id.note_cost_edit_text);
                    mPresenter.onAddNoteSubmit(e1.getText().toString(), e2.getText().toString(), e3.getText().toString());
                    dialog.cancel();
                })
                .setNegativeButton(getResources().getString(R.string.dialog_cansel), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void clearRecycler() {
        mAdapter.clear();
    }

    @OnClick(R.id.budget_button_plus)
    public void onPlusButtonClick() {
        mPresenter.onAddButtonClick(mEditText.getText().toString(), false);
    }

    @OnClick(R.id.budget_button_minus)
    public void onMinusButtonClick() {
        mPresenter.onAddButtonClick(mEditText.getText().toString(), true);
    }

    @OnClick(R.id.budget_loadmore)
    public void onLoadMoreButtonClick() {
        mPresenter.onLoadMoreButtonClick();
    }
}
