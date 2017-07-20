package ru.idaspin.helperforbulgakov.activities.recorder;

import android.support.annotation.NonNull;

import ru.idaspin.helperforbulgakov.R;
import ru.yandex.speechkit.Error;

/**
 * Created by idaspin.
 * Date: 7/18/2017
 * Time: 1:13 PM
 */

class RecorderPresenter {

    private final RecorderView mView;
    private boolean mRecognizerState = false;

    RecorderPresenter(@NonNull RecorderView view) {
        mView = view;
    }

    void init() {
        mView.setupViews();
    }

    void onFabClick() {
        if (mRecognizerState) {
            mView.stopRecognizer();
            mRecognizerState = false;
        } else {
            mView.recreateRecognizer();
            mView.startRecognizer();
            mRecognizerState = true;
        }
    }

    void onRecordingBegin() {
        mView.setStatus(R.string.recognizer_begin, null);
    }

    void onSpeechDetected() {
        mView.setStatus(R.string.recognizer_speech_detected, null);
    }

    void onSpeechEnds() {
        mView.setStatus(R.string.recognizer_speech_end, null);
    }

    void onRecordingDone() {
        mView.setStatus(R.string.recognizer_done, null);
    }

    void onSoundDataRecorded() {
        // do nothing
    }

    void onPowerUpdated() {
        // do nothing
    }

    void onPartialResults(String result) {
        mView.setStatus(R.string.recognizer_part, result);
    }

    void onRecognitionDone(String s, String result) {
        mView.setResult(s + result);
    }

    void onError(int code, String string) {
        if (code == Error.ERROR_CANCELED) {
            mView.setStatus(R.string.recognizer_canseled, null);
        } else {
            mView.setStatus(R.string.recognizer_error, string);
            mView.resetRecognizer();
        }
        mRecognizerState = false;
    }

    void onPause() {
        mView.resetRecognizer();
        mRecognizerState = false;
    }

    boolean onOptionsItemSelected(int itemId) {
        switch (itemId) {
            case R.id.nav_save:
                mView.showSaverDialog();
                return true;
            case R.id.nav_remove:
                mView.setResult("");
                return true;
            default:
                return false;
        }
    }

    void onTitleChoosed(String s) {
        mView.setupSaver(s);
    }

    void onFileSaved() {
        mView.showFileDialog();
    }

    void onChoosedToOpenFile() {
        mView.openFileFolder();
    }

    void onFileWritingError() {
        mView.showErrorDialog(R.string.dialog_recorder_error_writer);
    }

    void onFileCreationError() {
        mView.showErrorDialog(R.string.dialog_recorder_error_creation);
    }
}
