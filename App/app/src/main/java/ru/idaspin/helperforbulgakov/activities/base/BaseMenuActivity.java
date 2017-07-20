package ru.idaspin.helperforbulgakov.activities.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Date;

import ru.idaspin.helperforbulgakov.R;
import ru.idaspin.helperforbulgakov.utils.Constants;
import ru.idaspin.helperforbulgakov.utils.SharedPrefsUtil;

/**
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:18 PM
 */

public abstract class BaseMenuActivity extends AppCompatActivity implements BaseMenuView, BottomNavigationView.OnNavigationItemSelectedListener {

    private BaseMenuPresenter       mPresenter;
    private BottomNavigationView    mBottomNavigationView;
    private FloatingActionButton    mFloatingActionButton;
    private SwipeRefreshLayout      mSwipeRefreshLayout;

    /**
     * Инициализирует настройку функционала BaseMenuActivity.
     * onCreateMenuActivity следует вызывать в методе onCreate наследника.
     * @param childId Идентификационный номер Лейаута, который следует загрузить в основное окно.
     */
    protected void onCreateMenuActivity(int childId) {
        setContentView(R.layout.activity_base);

        SharedPrefsUtil.setLongPreference(this, Constants.SHARED_LAST + getLocalClassName(), 0);
        mPresenter = new BaseMenuPresenter(this);
        mPresenter.init(childId, this.getLocalClassName());
    }

    /**
     * Отображает тост
     * @param message - id ресурса
     */
    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Делает кнопку {@link FloatingActionButton} видимой
     * @param listener интерфейс слушателя
     * @param icon его иконка (R.drawable.icon например)
     */
    protected void setupFloatingActionButton(View.OnClickListener listener, int icon){
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mFloatingActionButton.setImageResource(icon);
        mFloatingActionButton.setOnClickListener(listener);
    }

    /**
     * Закрывает всё приложение
     */
    @Override
    public void closeApp() {
        finishAffinity();
    }

    /**
     * Отображает тост
     * @param message - id ресурса
     */
    @Override
    public void showToastMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Сообщает наследнику, если окно активити не было активно последние 10 минут
     */
    @Override
    public abstract void onResumeListenerForRequest();

    /**
     * Принуждает наследников указать текст подзаголовка в тулбаре.
     */
    protected abstract int setActivityTitle();

    @Override
    public void setupContainerBase(int id) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.base_container);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = layoutInflater.inflate(id, (ViewGroup) findViewById(R.id.container_layout), false);
        frameLayout.addView(childLayout);
    }

    @Override
    public void onResume(){
        super.onResume();
        mPresenter.onResume(SharedPrefsUtil.getLongPreference(this, Constants.SHARED_LAST + getLocalClassName(), 0));
        SharedPrefsUtil.setLongPreference(this, Constants.SHARED_LAST + getLocalClassName(), new Date(System.currentTimeMillis()).getTime());
    }

    @Override
    public void setupViewsBase() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.base_bottom_menu);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.base_fab);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle(setActivityTitle());
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.base_swipe_refresh_layout);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return mPresenter.onNavigationItemSelected(item.getItemId());
    }

    /**
     * Открытие активити
     * @param activity - class name требуемого активити
     */
    @Override
    public void openActivity(Class<? extends BaseMenuActivity> activity) {
        Intent intent = new Intent(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void setupSwipeRefreshLayout(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    @Override
    public void hideSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void doBackPress() {
        super.onBackPressed();
    }

    @Override
    public void setMenuIconSelected(int i) {
        mBottomNavigationView.setSelectedItemId(i);
    }
}


