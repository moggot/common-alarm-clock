package com.moggot.commonalarmclock.presentation.mvp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.moggot.commonalarmclock.R;
import com.moggot.commonalarmclock.domain.analytics.Analysis;

public class BuzzerSnoozeFragment extends Fragment {

    private String name;
    private Analysis analysis;
    private SnoozeListener listener;
    private static final String EXTRA_NAME = "name";

    public interface SnoozeListener {
        void onClickSnooze();
    }

    public BuzzerSnoozeFragment() {
    }

    public static BuzzerSnoozeFragment newInstance(String name) {
        BuzzerSnoozeFragment buzzerSnoozeFragment = new BuzzerSnoozeFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        buzzerSnoozeFragment.setArguments(args);
        return buzzerSnoozeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.listener = (SnoozeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SnoozeListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.analysis = new Analysis(getContext());
        analysis.sendScreenName(this.getClass().getSimpleName());

        if (getArguments() != null)
            this.name = getArguments().getString(EXTRA_NAME);
    }

    @Override
    public void onResume() {
        super.onResume();
        analysis.sendScreenName(getClass().getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_snooze, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnStop = (Button) view.findViewById(R.id.btnStop);
        Button btnSnooze = (Button) view.findViewById(R.id.btnSnooze);

        ((TextView) view.findViewById(R.id.tvName)).setText(name);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSnooze();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}