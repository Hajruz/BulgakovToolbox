package ru.idaspin.helperforbulgakov.utils;

/**
 * Класс Constants - набор ключевых полей, которые могут быть спользованы по всей области приложения.
 *
 * Created by idaspin.
 * Date: 7/18/2017
 * Time: 8:09 PM
 */

public class Constants {

    /**
     * Используется как ключевое значение при получении/установки параметров в {@link android.content.SharedPreferences}
     * SHARED_LAST - только приставка для ключа. Его второй частью является - getLocalClassName(), для организации уникальных ключей для каждого активити.
     */
    public static final String SHARED_LAST = "shared_last_";
    /**
     * Используется как ключевое значение при получении/установки параметров в {@link android.content.SharedPreferences}
     */
    public static final String SHARED_BUDGET_COUNT = "shared_budget_count";

    /**
     * OnActivityResult ключ
     */
    public static final int REQUEST_CODE_FILE = 1;
    /**
     * OnActivityResult ключ
     */
    public static final int REQUEST_CODE_IMAGE = 2;

}
