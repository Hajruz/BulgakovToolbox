package ru.idaspin.helperforbulgakov.activities_old.books;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.content.Book;
import ru.idaspin.helperforbulgakov.activities_old.menu.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.widgets.EmptyRecyclerView;

/**
 * Created by jerdys.
 * Date: 14.07.2017
 * Time: 1:26 PM
 */

public class BooksListActivity extends BaseMenuActivity {
    private EmptyRecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<Book> books;
    private FloatingActionButton add;
    private final int REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;

    private String fileName = "";

    @Override
    protected int setActivityTitle() {
        return R.string.title_books;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.old_activity_books_list);

        recyclerView = (EmptyRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        books = new ArrayList<>();
        adapter = new BooksAdapter();

        recyclerView.setAdapter(adapter);
        adapter.attachToRecyclerView(recyclerView);

        setupFloatingActionButton(v -> fileChooser(), R.mipmap.ic_add_button);
    }

    private void fileChooser() {
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intentPDF, "Select a file to upload"),
                    REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText
                    (BooksListActivity.this, "File Manager not found. " +
                                    "Please install File Manager first",
                            Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String displayName;
            Cursor cursor = null;

            if (uriString.startsWith("content://")) {
                try {
                    cursor = getContentResolver().query(uri, null, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex
                                (OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                //displayName = myFile.getName();
                //setTitle("Example 2");
            }
        }

        Intent intent = new Intent(this, BookEditorActivity.class);
        startActivity(intent);
    }

    public void setFileName(String title) {
        //fileName = intent.getData().getLastPathSegment();
        fileName = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void addBooks(List<Book> listOfBooks) {
        //adapter.notifyItemInserted(books.size() - 1);
        books = listOfBooks;
        adapter.changeDataSet(books);
    }

    private void uploadFile(Uri fileUri) {
        //TODO
    }
}
