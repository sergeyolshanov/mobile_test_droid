package com.demo.mobile_test_droid.screens.search_contacts;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.mobile_test_droid.adapters.ContactsAdapter;
import com.demo.mobile_test_droid.api.ApiFactory;
import com.demo.mobile_test_droid.api.ApiService;
import com.demo.mobile_test_droid.data.ContactsDatabase;
import com.demo.mobile_test_droid.pojo.Contacts;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactsViewModel extends AndroidViewModel {

    private static ContactsDatabase db;
    private LiveData<List<Contacts>> contacts;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable1;
    private Disposable disposable2;
    private Disposable disposable3;
    private ApiService apiService;
    private List<Contacts> contactsList = new ArrayList<>();
    private ContactsAdapter adapter = new ContactsAdapter(contactsList);

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        db = ContactsDatabase.getInstance(application);
        contacts = db.contactsDAO().getAllContacts();
        errors = new MediatorLiveData<>();
    }

    public LiveData<List<Contacts>> getContacts() {
        return contacts;
    }

    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors(){
        errors.setValue(null);
    }


    @SuppressWarnings("unchecked")
    private void insertContacts(List<Contacts> contacts) {
        new InsertContactsTask().execute(contacts);
    }

    private static class InsertContactsTask extends AsyncTask<List<Contacts>, Void, Void> {
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Contacts>... lists) {
            if(lists != null && lists.length > 0) {
                db.contactsDAO().insertContacts(lists[0]);
            }
            return null;
        }
    }

    private void deleteAllContacts() {
        new DeleteAllContacts().execute();
    }

    private static class DeleteAllContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            db.contactsDAO().deleteAllContacts();
            return null;
        }
    }

    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();

        fetchData();
    }

    private void fetchData() {
        disposable1 = (Disposable) apiService.getContacts1()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contacts>>() {
                    @Override
                    public void accept(List<Contacts> contacts) throws Exception {
                        deleteAllContacts();
                        insertContacts(contacts);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        errors.setValue(throwable);
                    }
                });

        disposable2 = (Disposable) apiService.getContacts2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contacts>>() {
                    @Override
                    public void accept(List<Contacts> contacts) throws Exception {
                        insertContacts(contacts);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        disposable3 = (Disposable) apiService.getContacts3()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contacts>>() {
                    @Override
                    public void accept(List<Contacts> contacts) throws Exception {
                        insertContacts(contacts);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        compositeDisposable.add(disposable1);
        compositeDisposable.add(disposable2);
        compositeDisposable.add(disposable3);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
