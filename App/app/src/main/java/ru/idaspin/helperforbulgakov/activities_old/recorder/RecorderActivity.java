package ru.idaspin.helperforbulgakov.activities_old.recorder;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.activities_old.menu.BaseMenuActivity;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;

import static android.Manifest.permission.RECORD_AUDIO;

public class RecorderActivity extends BaseMenuActivity implements RecognizerListener {

    private Recognizer recognizer;
    private TextView textViewStatus;
    private EditText editTextResult;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bulgakov";
    private Button cancel;
    private Button ok;
    private EditText editTextForSave;
    String result = "";
    int count = 0;

    @Override
    protected int setActivityTitle() {
        return R.string.title_recorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateMenuActivity(R.layout.old_activity_recorder);
        SpeechKit.getInstance().configure(getApplicationContext(), "fd2c9ae3-764b-4bb0-8b35-9428ce31d85b");
        textViewStatus = (TextView) findViewById(R.id.status);
        editTextResult = (EditText) findViewById(R.id.result);
        cancel = (Button) findViewById(R.id.cancel_button);
        ok = (Button) findViewById(R.id.ok_button);

        setupFloatingActionButton(v -> {
            if (count % 2 == 0) {
                createAndStartRecognizer();
            } else {
                   recognizer.finishRecording();
            }
            count++;
        }, R.mipmap.ic_microphone);



        File dir = new File(path);
        dir.mkdirs();


    }

    public void actionBarSave() {
        editTextResult.setText(editTextResult.getText().toString());
        Dialog dialog = new Dialog(RecorderActivity.this);
        dialog.setContentView(R.layout.old_dialog_record);
        dialog.setTitle("The file's name");
        dialog.show();
        cancel = (Button) dialog.findViewById(R.id.cancel_button);
        ok = (Button) dialog.findViewById(R.id.ok_button);
        editTextForSave = (EditText) dialog.findViewById(R.id.edit_for_saving);
        cancel.setOnClickListener(v1 -> dialog.dismiss());
        ok.setOnClickListener(v12 -> {
            if (!editTextForSave.getText().toString().equals("")) {
                File file = new File(path + "/" + editTextForSave.getText().toString() + ".txt");
                String text = editTextResult.getText().toString();
                editTextResult.setText("");
                result = "";
                Toast.makeText(getApplicationContext(), "Text saved to external storage/bulgakov", Toast.LENGTH_SHORT).show();
                Save(file, text);
                dialog.dismiss();
            }
        });
    }

    public void actionBarRemove() {
        editTextResult.setText("");
        result = "";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.old_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_save:
                actionBarSave();
                return true;
            case R.id.nav_remove:
                actionBarRemove();
                return true;
        }
        return false;
    }

    public void createAndStartRecognizer() {
        if (ActivityCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{RECORD_AUDIO}, 1);
            }
        } else {
            recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, RecorderActivity.this);
            recognizer.start();
        }


    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        updateStatus("Recording begin ");
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {
        updateStatus("Speech detected ");
    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {
        updateStatus("Speech end ");

    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {

        updateStatus("Recording done ");
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {
        //do nothing
    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {
        //do nothing
    }


    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
        updateStatus("Partial results \n\r" + recognition.getBestResultText());
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        result = editTextResult.getText().toString();
        result = result + recognition.getBestResultText();
        updateResult(result);
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
        if (error.getCode() == Error.ERROR_CANCELED) {
            updateStatus("Cancelled");
        } else {
            updateStatus("Error occurred " + error.getString());
            resetRecognizer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        resetRecognizer();
    }

    private void resetRecognizer() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer = null;
        }
    }

    public void updateStatus(final String text) {
        textViewStatus.setText(text);
    }

    public void updateResult(String text) {
        editTextResult.setText(text);
        editTextResult.setSelection(editTextResult.getText().length());
    }

    public static void Save(File file, String data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                fos.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
