package com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Beneficiary;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.model.Person;
import com.ikhiloyaimokhai.dependentworkmanagerperiodictask_kotlin.util.Constant;

@Database(entities = {Person.class, Beneficiary.class},
        version = 1, exportSchema = false)
public abstract class CommunityDatabase extends RoomDatabase {
    private static volatile CommunityDatabase INSTANCE;

    public abstract CommunityDao employeeDao();

    public static CommunityDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommunityDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CommunityDatabase.class, Constant.EMPLOYEE_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
