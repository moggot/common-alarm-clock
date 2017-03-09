package com.moggot.commonalarmclock.alarm;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.moggot.commonalarmclock.alarm.Alarm;

import com.moggot.commonalarmclock.alarm.AlarmDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig alarmDaoConfig;

    private final AlarmDao alarmDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        alarmDaoConfig = daoConfigMap.get(AlarmDao.class).clone();
        alarmDaoConfig.initIdentityScope(type);

        alarmDao = new AlarmDao(alarmDaoConfig, this);

        registerDao(Alarm.class, alarmDao);
    }
    
    public void clear() {
        alarmDaoConfig.getIdentityScope().clear();
    }

    public AlarmDao getAlarmDao() {
        return alarmDao;
    }

}
