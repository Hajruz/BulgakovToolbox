package ru.idaspin.helperforbulgakov.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Набор getter'ов и setter'ов для чтения/записи данных через {@link SharedPreferences}.
 *
 * Created by idaspin.
 * Date: 7/14/2017
 * Time: 3:27 PM
 */

final public class SharedPrefsUtil {
    private SharedPrefsUtil() {}

    /**
     * Helper-метод: Получаем String параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @return значение из хранилища или стандартное значение.
     */
    public static String getStringPreference(Context context, String key) {
        String value = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getString(key, null);
        }
        return value;
    }

    /**
     * Helper-метод: Записывает String параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param value Значение
     * @return true если значение успешно записано.
     */
    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper-метод: Получаем Float параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param defaultValue Значение
     * @return значение из хранилища или стандартное значение.
     */
    public static float getFloatPreference(Context context, String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper-метод: Записывает Float параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param value Значение
     * @return true если значение успешно записано.
     */
    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper-метод: Получаем Long параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param defaultValue Значение
     * @return значение из хранилища или стандартное значение.
     */
    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper-метод: Записывает Long параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param value Значение
     * @return true если значение успешно записано.
     */
    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper-метод: Получаем Integer параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param defaultValue Значение
     * @return значение из хранилища или стандартное значение.
     */
    public static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper-метод: Записывает Integer параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param value Значение
     * @return true если значение успешно записано.
     */
    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper-метод: Получаем boolean параметр из {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param defaultValue Значение
     * @return значение из хранилища или стандартное значение.
     */
    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper-метод: Записывает boolean параметр в {@link SharedPreferences}.
     *
     * @param context {@link Context} контекст.
     * @param key Ключ
     * @param value Значение
     * @return true если значение успешно записано.
     */
    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return value;
    }
}