package ru.idaspin.helperforbulgakov.activities_old.books;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.widgets.BaseRecyclerAdapter;

/**
 * Created by jerdy.
 * Date: 18.07.2017
 * Time: 1:26 PM
 */

public class BooksAdapter extends BaseRecyclerAdapter<BooksHolder, Book> {

    @Override
    public BooksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.old_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BooksHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.bind(getItem(position));
    }
}
