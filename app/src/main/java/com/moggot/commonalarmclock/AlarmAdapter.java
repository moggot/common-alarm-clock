package com.moggot.commonalarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moggot.commonalarmclock.animation.AnimationBounce;
import com.moggot.commonalarmclock.animation.DeleteAlarmAnimationBounce;
import com.moggot.commonalarmclock.observer.AdapterDisplay;
import com.moggot.commonalarmclock.observer.AlarmData;
import com.moggot.commonalarmclock.alarm.Alarm;

import java.util.List;

/**
 * Created by toor on 06.03.17.
 */

public class AlarmAdapter extends BaseAdapter {

    private final static String LOG_TAG = AlarmAdapter.class.getSimpleName();

    private static class ViewHolder {
        private TextView tvDays;
        private TextView tvTime;
        private TextView tvName;
        private ToggleButton tgState;
        private Button ivDelete;
        private ImageView ivMath;
        private ImageView ivSnooze;
        private ImageView ivMusicType;
    }

    private Context context;
    private List<Alarm> alarms;
    private LayoutInflater inflater;
    private DataBase db;

    public AlarmAdapter(Context context, List<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.db = new DataBase(context);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return alarms.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    private Alarm getAlarm(int position) {
        return ((Alarm) getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.alarm_item, parent, false);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tgState = (ToggleButton) view
                .findViewById(R.id.tgAdapterState);
        viewHolder.tvDays = (TextView) view.findViewById(R.id.tvAdapterDays);
        viewHolder.tvTime = (TextView) view.findViewById(R.id.tvAdapterTime);
        viewHolder.tvName = (TextView) view.findViewById(R.id.tvAdapterName);
        viewHolder.ivDelete = (Button) view
                .findViewById(R.id.btnAdapterDelete);
        viewHolder.ivMath = (ImageView) view
                .findViewById(R.id.ivAdapterMath);
        viewHolder.ivSnooze = (ImageView) view
                .findViewById(R.id.ivAdapterSnooze);
        viewHolder.ivMusicType = (ImageView) view
                .findViewById(R.id.ivAdapterMusicType);

        viewHolder.tgState.setTag(alarms.get(position));
        viewHolder.ivDelete.setTag(alarms.get(position));
        viewHolder.tvTime.setTag(alarms.get(position));
        viewHolder.tvDays.setTag(alarms.get(position));
        viewHolder.tvName.setTag(alarms.get(position));
        viewHolder.ivMath.setTag(alarms.get(position));
        viewHolder.ivSnooze.setTag(alarms.get(position));
        viewHolder.ivMusicType.setTag(alarms.get(position));

        final Alarm alarm = getAlarm(position);
        AlarmData alarmData = new AlarmData();
        AdapterDisplay adapterDisplay = new AdapterDisplay(context, view, alarmData);
        alarmData.setAlarm(alarm);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySettings.class);
                intent.putExtra(Consts.EXTRA_ID, alarm.getId());
                ((Activity) context).startActivityForResult(intent, Consts.REQUEST_CODE_ACTIVITY_SETTINGS);
            }

        });

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AnimationBounce animationBounce = new DeleteAlarmAnimationBounce(context);
                animationBounce.animate(view);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(context.getString(R.string.dialog_title_remove));
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlarmContext alarmContext = new AlarmContext(alarm, context);
                                AlarmManager alarmManager = new AlarmManager();
                                alarmManager.cancelAlarm(alarmContext);
                                db.deleteAlarm(alarm);
                                alarms.remove(position);
                                update(alarms);
                            }
                        })
                        .setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        viewHolder.tgState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlarmContext alarmContext = new AlarmContext(alarm, context);
                AlarmManager alarmManager = new AlarmManager();
                if (isChecked)
                    alarmManager.setAlarm(alarmContext);
                else
                    alarmManager.cancelAlarm(alarmContext);
            }
        });
        return view;
    }

}
