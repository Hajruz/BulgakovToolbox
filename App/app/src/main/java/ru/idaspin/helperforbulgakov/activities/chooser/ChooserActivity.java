package ru.idaspin.helperforbulgakov.activities.chooser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities.base.BaseMenuActivity;
import ru.idaspin.helperforbulgakov.utils.Constants;
import ru.idaspin.helperforbulgakov.utils.FileUtil;

/**
 * Updated by idaspin.
 * Date: 7/19/2017
 * Time: 2:23 AM
 * + Data binding
 * + MVP integrated
 * + Re:design
 * + Updated Image & File (pdf) pickers functionality
 */

public class ChooserActivity extends BaseMenuActivity implements ChooserView {

    @BindView(R.id.chooser_poster)      ImageView mImageView;
    @BindView(R.id.chooser_title)       EditText  mEditTextTitle;
    @BindView(R.id.chooser_description) EditText  mEditTextDescr;

    private ChooserPresenter mPresenter;
    private ProgressDialog   mShow;

    @Override
    public void finish(){
        if (mShow != null) {
            mShow.dismiss();
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.content_chooser);
        ButterKnife.bind(this);

        mPresenter = new ChooserPresenter(this);
        mPresenter.init();
    }

    @Override
    public void onResumeListenerForRequest() {
        mPresenter.onResumeListenerForRequest();
    }

    @Override
    protected int setActivityTitle() {
        return R.string.title_chooser;
    }

    @Override
    public void openFileChooser() {
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intentPDF, getString(R.string.dialog_chooser_file)), Constants.REQUEST_CODE_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            showToastMessage(R.string.chooser_no_file_manager);
        }
    }

    @Override
    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, getString(R.string.dialog_chooser_image)), Constants.REQUEST_CODE_IMAGE);
    }

    @Override
    public void closeActivity(int result) {
        setResult(result, new Intent());
        finish();
    }

    @Override
    public void getFilePath(Uri uri) {
        String s = "";
        try {
            s = FileUtil.getPath(this, uri);
        } catch (URISyntaxException ignored) {
            closeActivity(RESULT_CANCELED);
        }
        mPresenter.onFilePathResult(s);
    }

    @Override
    public void getImagePath(Uri uri) {
        try {
            mPresenter.onImagePathResult(FileUtil.getPath(this, uri));
        } catch (URISyntaxException ignored) {
            showToastMessage(R.string.chooser_error_image);
        }
    }

    @Override
    public void displayPoster(String path) {
        if (path.contains("storage")) {
            File f = new File(path);
            Picasso.with(this)
                    .load(f)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        } else {
            Picasso.with(this)
                    .load(path)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }
    }

    @Override
    public void showLoading(boolean b) {
        if (b) {
            mShow = ProgressDialog.show(ChooserActivity.this, "", getResources().getString(R.string.message_loading), true);
            mShow.setCancelable(false);
        } else if (mShow != null) {
            mShow.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chooser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return mPresenter.onOptionsItemSelected(menuItem.getItemId(), mEditTextTitle.getText().toString(), mEditTextDescr.getText().toString());
    }

    @OnClick(R.id.chooser_button)
    public void onButtonClick() {
        mPresenter.onButtonClick();
    }
}
