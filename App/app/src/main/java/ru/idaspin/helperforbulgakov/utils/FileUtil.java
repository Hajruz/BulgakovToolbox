package ru.idaspin.helperforbulgakov.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.net.URISyntaxException;

/**
 * Created by idaspin.
 * Date: 7/19/2017
 * Time: 3:19 AM
 */

public class FileUtil {

    /**
     * Возвращает путь к файлу из URI
     * @param context {@link Context}
     * @param uri {@link Uri} запрашиваемого файла
     * @return Строку, которая содержит путь к файлу
     * @throws URISyntaxException Для неккоректных {@link Uri}
     */
    public static String getPath(Context context, Uri uri) throws URISyntaxException {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = 0;
                if (cursor != null) {
                    column_index = cursor.getColumnIndexOrThrow("_data");
                }
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

}
