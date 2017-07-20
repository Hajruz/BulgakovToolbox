package ru.idaspin.helperforbulgakov.activities.chooser;

import android.net.Uri;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 2:23 AM
 */

interface ChooserView {

    void openFileChooser();

    void closeActivity(int resultCanceled);

    void getFilePath(Uri uri);

    void openImageChooser();

    void showToastMessage(int chooser_error_image);

    void getImagePath(Uri uri);

    void displayPoster(String path);

    void showLoading(boolean b);
}
