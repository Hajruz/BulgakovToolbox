package ru.idaspin.helperforbulgakov.activities.library;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.widgets.BaseRecyclerAdapter;

/**
 * Created by User.
 * Date: 16.07.2017
 * Time: 9:21 PM
 */

class BooksAdapter extends BaseRecyclerAdapter<BooksHolder, Book> {

    @Override
    public BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false));
    }
    @Override
    public void onBindViewHolder(BooksHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bind(getItem(position));
    }
}
