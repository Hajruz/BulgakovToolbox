package ru.idaspin.helperforbulgakov.activities_old.books;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;

/**
 * Created by jerdy.
 * Date: 18.07.2017
 * Time: 1:26 PM
 */

public class BooksHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.cover) ImageView cover;
    @BindView(R.id.linear_layout) LinearLayout linearLayout;

    public BooksHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Book book) {
        title.setText(book.getTitle());
        description.setText(book.getDescription());
        //cover.setImageURI(book.getPoster());
    }
}
