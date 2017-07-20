package ru.idaspin.helperforbulgakov;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;
import ru.idaspin.helperforbulgakov.api.ApiFactory;
import ru.idaspin.helperforbulgakov.repository.RepositoryProvider;

/**
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:15 PM
 */

public class AppDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        ApiFactory.recreate();
        RepositoryProvider.init();
    }

}
