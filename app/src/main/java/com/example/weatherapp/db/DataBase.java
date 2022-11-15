package com.example.weatherapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Repo.class,History.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    private static final String DB_NAME = "database.db";
    private static volatile DataBase instance;

    // region singleton implementation
    public static synchronized DataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static synchronized DataBase getTestInstance(Context context) {
        if (instance == null) {
            instance = createTestDataBase(context);
        }
        return instance;
    }
    private static DataBase create(final Context context) {
        Builder<DataBase> builder = Room.databaseBuilder(context, DataBase.class, DB_NAME);
        return builder.build();
    }
    private static DataBase createTestDataBase(final Context context) {
        Builder<DataBase> builder = Room.inMemoryDatabaseBuilder(context, DataBase.class).
                allowMainThreadQueries();
        return builder.build();
    }

    // endregion
    // region DAOs
    public abstract RepoDao repoDao();
}
