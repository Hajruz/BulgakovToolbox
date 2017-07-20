package ru.idaspin.helperforbulgakov.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * Координатор работы репозитория
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:18 PM
 */

public final class RepositoryProvider {

    private static BulgakovRepository sRisensRepository;

    private RepositoryProvider() {
    }

    @MainThread
    public static void init() {
        sRisensRepository = new DefaultBulgakovRepository();
    }

    /**
     * Получение актуального репозитория
     * @return {@link BulgakovRepository}
     */
    @NonNull
    public static BulgakovRepository provideRisensRepository() {
        if (sRisensRepository == null) {
            sRisensRepository = new DefaultBulgakovRepository();
        }
        return sRisensRepository;
    }

}
