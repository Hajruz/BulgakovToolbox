package ru.idaspin.helperforbulgakov.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Класс EmptyRecyclerView - {@link RecyclerView}, способный изменить атрибуты прикрепленных к нему View элементов
 * динамически, при изменении стостояния своего адаптера.
 *
 * Created by idaspin.
 * Date: 7/6/2017
 * Time: 8:09 PM
 */

public class EmptyRecyclerView extends RecyclerView {

    @Nullable
    private View mEmptyView;
    @Nullable
    private View mLoadView;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public void setEmptyView(@NonNull View view) {
        mEmptyView = view;
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void checkIfEmpty() {
        if (getAdapter().getItemCount() > 0) {
            showRecycler();
        } else {
            showEmptyView();
        }
    }

    @VisibleForTesting
    void showRecycler() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
        if (mLoadView != null && mLoadView.getTag().equals(true)) {
            mLoadView.setVisibility(VISIBLE);
        }
        setVisibility(VISIBLE);
    }

    @VisibleForTesting
    void showEmptyView() {
        setVisibility(GONE);
        if (mEmptyView != null) {
            mEmptyView.setVisibility(VISIBLE);
        }
        if (mLoadView != null) {
            mLoadView.setVisibility(GONE);
        }
    }

    public void setLoadView(@Nullable View loadView) {
        mLoadView = loadView;
    }
}