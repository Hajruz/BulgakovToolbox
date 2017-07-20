package ru.idaspin.helperforbulgakov.activities.recorder;

import android.support.annotation.Nullable;

/**
 * Created by idaspin.
 * Date: 7/18/2017
 * Time: 1:14 PM
 */

interface RecorderView {

    void setupViews();

    void startRecognizer();

    void setStatus(int message, @Nullable String result);

    void setResult(String s);

    void resetRecognizer();

    void setupSaver(String name);

    void showSaverDialog();

    void showFileDialog();

    void openFileFolder();

    void stopRecognizer();

    void recreateRecognizer();

    void showErrorDialog(int message);
}
