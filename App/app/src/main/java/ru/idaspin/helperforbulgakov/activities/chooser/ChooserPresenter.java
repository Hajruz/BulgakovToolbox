package ru.idaspin.helperforbulgakov.activities.chooser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;
import ru.idaspin.helperforbulgakov.utils.Constants;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 2:23 AM
 */

class ChooserPresenter {

    private String mFile = "";
    private String mImage = "";

    private final ChooserView mView;

    ChooserPresenter(@NonNull ChooserView view) {
        mView = view;
    }

    void init() {
        mView.openFileChooser();
    }

    void onResumeListenerForRequest() {
        // do nothing
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_FILE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                mView.getFilePath(uri);
            } else {
                mView.closeActivity(RESULT_CANCELED);
            }
        } else {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                mView.getImagePath(uri);
            } else {
                mView.showToastMessage(R.string.chooser_error_image);
            }
        }
    }

    void onFilePathResult(String path) {
        if (path == null) {
            mView.closeActivity(RESULT_CANCELED);
        }
        mFile = path;
    }

    boolean onOptionsItemSelected(int itemId, String title, String description) {
        if (itemId == R.id.nav_save && title != null && title.length() > 0 && description != null && description.length() > 0) {
            mView.showLoading(true);
            RepositoryProvider.provideRisensRepository()
                    .addBook(title, description, mImage, mFile)
                    .subscribe(o -> {
                        mView.showLoading(false);
                        mView.closeActivity(RESULT_OK);
                    }, throwable -> {
                        mView.showLoading(false);
                        mView.closeActivity(RESULT_OK);
                    });
            return true;
        }
        return false;
    }

    void onButtonClick() {
        mView.openImageChooser();
    }

    void onImagePathResult(String path) {
        mImage = path;
        mView.displayPoster(path);
    }
}