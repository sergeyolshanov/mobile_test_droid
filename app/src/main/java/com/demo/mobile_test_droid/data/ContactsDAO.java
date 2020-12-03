package com.demo.mobile_test_droid.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.demo.mobile_test_droid.pojo.Contacts;
import java.util.List;

@Dao
public interface ContactsDAO {

    @Query("SELECT * FROM contacts")
    LiveData<List<Contacts>> getAllContacts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContacts(List<Contacts> contacts);

    @Query("DELETE FROM contacts")
    void deleteAllContacts();
}
