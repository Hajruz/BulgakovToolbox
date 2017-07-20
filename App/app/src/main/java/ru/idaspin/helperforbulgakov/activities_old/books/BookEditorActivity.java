package ru.idaspin.helperforbulgakov.activities_old.books;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities_old.menu.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Created by jerdys.
 * Date: 16.07.2017
 * Time: 1:26 PM
 */

public class BookEditorActivity extends BaseMenuActivity {
    ImageView imageViewCover;
    EditText editTextTitle, editTextDescription;
    Button chooseBookCover;
    BooksListActivity booksListActivity;
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bulgakov";
    final String BOOK_TITLE = "title", BOOK_DESCRIPTION = "description";
    final int PICK_IMAGE_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    String coverPath;
    Bitmap bookCoverPreview;
    Uri testUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.old_activity_file_editor);

        editTextTitle = (EditText) findViewById(R.id.book_title);
        editTextDescription = (EditText) findViewById(R.id.book_description);
        chooseBookCover = (Button) findViewById(R.id.change_book_cover);
        booksListActivity = new BooksListActivity();

        editTextTitle.setText(booksListActivity.getFileName());
        //editTextDescription.setText("");

        chooseBookCover.setOnClickListener(v -> coverChooser());
    }

    private void coverChooser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Cover"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();

            setTestUri(uri);
            setBookCoverPreview(uri);
        }
    }

    @Override
    protected int setActivityTitle() {
        return R.string.app_name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.old_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_save:
                actionBarSave();

                return true;

            case R.id.nav_remove:
                actionBarRemove();

                return true;
        }

        return false;
    }

    public void actionBarSave() {

        //TODO add path to book cover

        /*RepositoryProvider.provideRisensRepository().addBook(editTextTitle.getText().toString(),
                editTextDescription.getText().toString(), getImgPath(getTestUri()), mFile)
                .subscribe((ignored) -> {

        }, throwable -> {

        });*/

        databaseRequest();

        finish();

        /*Intent intent = new Intent(this, BooksListActivity.class);
        startActivity(intent);*/
    }

    public void actionBarRemove() {
        //TODO
    }

    public Uri getImageUri1(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage
                (inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getImgPath(Uri uri) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 1);
            }
        }
        String[] largeFileProjection = { MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA };
        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        Cursor myCursor = this.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                largeFileProjection, null, null, largeFileSort);
        String largeImagePath = "";

        try {
            myCursor.moveToFirst();
            largeImagePath = myCursor.getString(myCursor
                            .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
        } finally {
            myCursor.close();
        }
        return largeImagePath;
    }
/*

    public Uri getImageUri2(Context context, Bitmap cover) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        cover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String coverPath = MediaStore.Images.Media.insertImage
                (context.getContentResolver(), cover, "Cover", null);

        return Uri.parse(coverPath);
    }
*/
/*

    public String getRealPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        cursor.moveToFirst();

        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

        return cursor.getString(index);
    }
*/

    public void setBookCoverPreview(Uri uri) {
        try {
            bookCoverPreview = MediaStore.Images.Media.getBitmap
                    (getContentResolver(), uri);
            imageViewCover = (ImageView) findViewById(R.id.book_cover);
            imageViewCover.setImageBitmap(bookCoverPreview);

            Toast.makeText(this, bookCoverPreview.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTestUri(Uri uri) {
        testUri = uri;
    }

    public Uri getTestUri() {
        return this.testUri;
    }

    /*public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }*/

    public void databaseRequest() {
        RepositoryProvider.provideRisensRepository().getBooks(0).subscribe((listOfBooks) -> {
            booksListActivity.addBooks(listOfBooks);
        }, (throwable -> {

        }));
    }
}
