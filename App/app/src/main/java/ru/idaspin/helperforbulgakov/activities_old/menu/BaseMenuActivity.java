package ru.idaspin.helperforbulgakov.activities_old.menu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import ru.idaspin.helperforbulgakov.R;

/**
 * Created by idaspin.
 * Date: 7/13/2017
 * Time: 1:18 PM
 */

public abstract class BaseMenuActivity extends AppCompatActivity implements BaseMenuView, BottomNavigationView.OnNavigationItemSelectedListener {

    private BaseMenuPresenter       mPresenter;
    private Toolbar                 mToolbar;
    private BottomNavigationView    mBottomNavigationView;
    private FloatingActionButton    mFloatingActionButton;

    protected void onCreateMenuActivity(int childId) {
        setContentView(R.layout.activity_base);

        mPresenter = new BaseMenuPresenter(this);
        mPresenter.init(childId, this.getLocalClassName());
    }

    @Override
    public void setupContainerBase(int id) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.base_container);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = layoutInflater.inflate(id, (ViewGroup) findViewById(R.id.container_layout), false);
        frameLayout.addView(childLayout);
    }

    @Override
    public void setupViewsBase() {
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.base_bottom_menu);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.base_fab);
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setSubtitle(setActivityTitle());
    }

    protected abstract int setActivityTitle();

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
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    @Override
    public void doBackPress() {
        super.onBackPressed();
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
     * Отображает тост
     * @param message - id ресурса
     */
    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Закрывает всё приложение
     */
    @Override
    public void closeApp() {
        finishAffinity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void setMenuIconSelected(int i) {
        mBottomNavigationView.setSelectedItemId(i);
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
}


