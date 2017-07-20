package ru.idaspin.helperforbulgakov.activities.recorder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities.base.BaseMenuActivity;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;

/**
 * Updated by idaspin.
 * Date: 7/18/2017
 * Time: 1:12 AM
 * + Data binding
 * + MVP integrated
 * + Re:design
 */

public class RecorderActivity extends BaseMenuActivity implements RecorderView, RecognizerListener {

    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bulgakov/";

    @BindView(R.id.recorder_status) TextView mTextView;
    @BindView(R.id.recorder_editor) EditText mEditText;

    private RecorderPresenter mPresenter;
    private Recognizer mRecognizer;
    private AlertDialog mAlert;

    @Override
    public void finish() {
        if (mAlert != null) {
            mAlert.dismiss();
        }
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.content_recorder);
        ButterKnife.bind(this);

        SpeechKit.getInstance().configure(getApplicationContext(), "fd2c9ae3-764b-4bb0-8b35-9428ce31d85b");
        mPresenter = new RecorderPresenter(this);
        mPresenter.init();
    }

    @Override
    protected int setActivityTitle() {
        return R.string.title_recorder;
    }

    @Override
    public void setupViews() {
        setupFloatingActionButton((v) -> mPresenter.onFabClick(), R.mipmap.ic_microphone);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void startRecognizer() {
        if (ActivityCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{RECORD_AUDIO}, 1);
            }
        }
        if (mRecognizer == null) {
            recreateRecognizer();
        }
        mRecognizer.start();
    }

    @Override
    public void recreateRecognizer() {
        mRecognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, RecorderActivity.this);
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        mPresenter.onRecordingBegin();
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {
        mPresenter.onSpeechDetected();
    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {
        mPresenter.onSpeechEnds();
    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        mPresenter.onRecordingDone();
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {
        mPresenter.onSoundDataRecorded();
    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {
        mPresenter.onPowerUpdated();
    }


    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
        mPresenter.onPartialResults(recognition.getBestResultText());
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        mPresenter.onRecognitionDone(mEditText.getText().toString(), recognition.getBestResultText());
    }

    @Override
    public void setResult(String s) {
        mEditText.setText(s);
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
        mPresenter.onError(error.getCode(), error.getString());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void resetRecognizer() {
        if (mRecognizer != null) {
            mRecognizer.cancel();
            mRecognizer = null;
        }
    }

    @Override
    public void setupSaver(String filename) {
        File dir = new File(PATH);
        dir.mkdirs();

        File file = new File(PATH + "/" + filename + ".txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ignored) {
                mPresenter.onFileCreationError();
            }
        }
        try {
            FileOutputStream writer = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            writer.write(mEditText.getText().toString().getBytes());
            writer.flush();
            writer.close();
        } catch (Exception ignored) {
            mPresenter.onFileWritingError();
        }
        mPresenter.onFileSaved();
    }

    @Override
    public void showSaverDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View dialogView = factory.inflate(R.layout.dialog_recorder, null);
        mAlert = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_recorder)
                .setView(R.layout.dialog_recorder)
                .setView(dialogView)
                .setPositiveButton(getResources().getString(R.string.dialog_choose), (dialog, which) -> {
                    EditText editText = (EditText) dialogView.findViewById(R.id.recorder_dialog_edittext);
                    mPresenter.onTitleChoosed(editText.getText().toString());
                    dialog.cancel();
                })
                .setNegativeButton(getResources().getString(R.string.dialog_cansel), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void onResumeListenerForRequest() {
        // do nothing
    }

    @Override
    public void showFileDialog() {
        mAlert = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_recorder_file)
                .setPositiveButton(getResources().getString(R.string.dialog_yes), (dialog, which) -> {
                    mPresenter.onChoosedToOpenFile();
                    dialog.cancel();
                })
                .setNegativeButton(getResources().getString(R.string.dialog_no), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void showErrorDialog(int message) {
        mAlert = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_error)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.dialog_yes), (dialog, which) -> {
                    mPresenter.onChoosedToOpenFile();
                    dialog.cancel();
                })
                .setNegativeButton(getResources().getString(R.string.dialog_no), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void openFileFolder() {
        /*
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Uri myUri = Uri.parse(PATH);
        intent.setDataAndType(myUri , "file*//*");
        startActivity(intent);
        */
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + "/bulgakov/");
        intent.setDataAndType(uri, "file/*");
        startActivity(Intent.createChooser(intent, "Открыть директорию"));
    }

    @Override
    public void stopRecognizer() {
        mRecognizer.finishRecording();
    }

    @Override
    public void setStatus(int message, @Nullable String result) {
        if (result != null) {
            mTextView.setText(String.format("%s %s", getResources().getString(message), result));
        } else {
            mTextView.setText(message);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item.getItemId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recorder_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
