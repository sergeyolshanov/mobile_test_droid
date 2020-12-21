package com.demo.mobile_test_droid.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.demo.mobile_test_droid.pojo.Contacts;

@Database(entities = {Contacts.class}, version = 1, exportSchema = false)
public abstract class ContactsDatabase extends RoomDatabase {

    private static final String DB_NAME = "coontacts.db";
    private static ContactsDatabase database;
    private static final Object LOCK = new Object();

    public static ContactsDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, ContactsDatabase.class, DB_NAME).build();
            }
            return database;
        }
    }

    /*public abstract ContactsDAO contactsDAO();*/
}
