package com.moggot.commonalarmclock;

import com.moggot.commonalarmclock.alarm.Alarm;

/**
 * Created by toor on 09.03.17.
 */

public class AlarmManager implements AlarmType {

    public void setAlarm(AlarmContext alarmContext) {
        AlarmOn on = new AlarmOn(alarmContext);
        Alarm alarm = alarmContext.getAlarm();
        if (alarm.getDays() == 0) {
            on.setType(new SingleAlarm());
        } else
            on.setType(new RepeateAlarm());
    }

    public void cancelAlarm(AlarmContext alarmContext) {
        AlarmOff off = new AlarmOff(alarmContext);
        Alarm alarm = alarmContext.getAlarm();
        if (alarm.getDays() == 0)
            off.setType(new SingleAlarm());
        else
            off.setType(new RepeateAlarm());
    }
}