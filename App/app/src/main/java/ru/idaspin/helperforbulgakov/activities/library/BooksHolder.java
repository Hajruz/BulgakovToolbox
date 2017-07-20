package ru.idaspin.helperforbulgakov.activities.library;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

/**
 * Created by User.
 * Date: 16.07.2017
 * Time: 9:22 PM
 *
 * Updated by idaspin.
 * Date: 7/19/2017
 * Time: 3:09 AM
 * + Re:design
 * + Attached poster
 * + PopupOverlay on item click (with functionality)
 */

class BooksHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.library_poster)  ImageView mImageView;
    @BindView(R.id.library_title)   TextView  mTextViewTitle;
    @BindView(R.id.library_message) TextView  mTextViewMessage;
    @BindView(R.id.library_options) ImageView mImageViewOptions;

    BooksHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    
    public void bind(@NonNull Book book) {
        if (!book.getPoster().isEmpty()) {
            if (book.getPoster().contains("storage")) {
                File f = new File(book.getPoster());
                Picasso.with(mImageView.getContext())
                        .load(f)
                        .resize(60, 80)
                        .centerCrop()
                        .into(mImageView);
            } else {
                Picasso.with(mImageView.getContext())
                        .load(book.getPoster())
                        .fit()
                        .centerCrop()
                        .into(mImageView);
            }
        } else {
            mImageView.setVisibility(View.GONE);
        }
        mTextViewTitle.setText(book.getTitle());
        mTextViewMessage.setText(String.format("%s…", book.getDescription().length() > 200 ? book.getDescription().substring(0, book.getDescription().indexOf(" ", 200)) : book.getDescription()));
        mImageViewOptions.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mImageViewOptions.getContext(), mImageViewOptions);
            if (book.getFile().length()>0) {
                popup.inflate(R.menu.book_options_menu);
            } else {
                popup.inflate(R.menu.book_options_menu_cut);
            }
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.nav_remove:
                        RepositoryProvider.provideRisensRepository()
                                .removeBook(book.getId())
                                .subscribe(o -> {
                                    Toast.makeText(mImageViewOptions.getContext(), R.string.library_dialog_removed, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(mImageViewOptions.getContext(), R.string.library_dialog_update, Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(mImageViewOptions.getContext(), R.string.library_dialog_removed, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(mImageViewOptions.getContext(), R.string.library_dialog_update, Toast.LENGTH_SHORT).show();
                                });
                        return true;
                    case R.id.nav_download:
                        File f = new File(book.getFile());
                        Uri selectedUri = Uri.parse(f.getPath().substring(0, f.getPath().lastIndexOf("/")));
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(selectedUri, "resource/folder");
                        if (intent.resolveActivityInfo(mImageViewOptions.getContext().getPackageManager(), 0) != null)
                        {
                            mImageViewOptions.getContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(mImageViewOptions.getContext(), R.string.chooser_no_file_manager, Toast.LENGTH_SHORT).show();
                        }
                        return true;
                }
                return false;
            });
            popup.show();
        });
    }
    
}
