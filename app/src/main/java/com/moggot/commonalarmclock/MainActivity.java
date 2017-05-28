package com.moggot.commonalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.android.gms.analytics.Tracker;
import com.moggot.commonalarmclock.analytics.FirebaseAnalysis;
import com.moggot.commonalarmclock.animation.AddAlarmAnimationBounce;
import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.mvp.main.MainModelImpl;
import com.moggot.commonalarmclock.mvp.main.MainPresenter;
import com.moggot.commonalarmclock.mvp.main.MainPresenterImpl;
import com.moggot.commonalarmclock.mvp.main.MainView;
import com.moggot.commonalarmclock.tutorial.OnboardingActivity;
import com.moggot.commonalarmclock.tutorial.SharedPreference;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private SwipeRecyclerViewAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startOnboarding();
        initAnalytics();

        setupMVP();
        setupViews();
    }

    private void startOnboarding() {
        if (SharedPreference.LoadTutorialStatus(this)) {
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);
        }
    }

    private void initAnalytics() {
        Tracker tracker = ((App) getApplication())
                .getDefaultTracker();
        tracker.enableAdvertisingIdCollection(true);

        FirebaseAnalysis firebaseAnalytics = new FirebaseAnalysis(this);
        firebaseAnalytics.init();
    }

    private void setupViews() {
        this.adapter = new SwipeRecyclerViewAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.alarmRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this, presenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupMVP() {
        MainPresenterImpl presenter = new MainPresenterImpl(this);
        MainModelImpl model = new MainModelImpl(getApplicationContext());
        presenter.setMainModel(model);
        this.presenter = presenter;
    }

    public void onClickAdd(View view) {
        AnimationBounce animation = new AddAlarmAnimationBounce(this);
        animation.animate(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Consts.REQUEST_CODE_ACTIVITY_SETTINGS:
                presenter.onActivityResult();
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void deleteAlarm(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        adapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}