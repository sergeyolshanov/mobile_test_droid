package com.demo.mobile_test_droid.screens.search_contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.demo.mobile_test_droid.R;
import com.demo.mobile_test_droid.adapters.ContactsAdapter;
import com.demo.mobile_test_droid.pojo.Contacts;
import com.demo.mobile_test_droid.screens.detailed.DetailedInformationActivity;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class SearchContactsListActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private RecyclerView recyclerViewContacts;
    private ContactsViewModel viewModel;
    private ContactsAdapter adapter;
    private ProgressBar progressBar;
    private CoordinatorLayout coordLayout;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = SearchContactsListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        coordLayout = (CoordinatorLayout) findViewById(R.id.constraint_main);
        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contacts) {
                adapter = new ContactsAdapter(contacts);
                progressBar.setVisibility(ProgressBar.VISIBLE);
                recyclerViewContacts.setAdapter(adapter);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                adapter.setListener(new ContactsAdapter.Listener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent(SearchContactsListActivity.this, DetailedInformationActivity.class);
                        intent.putExtra(DetailedInformationActivity.EXTRA_CONTACT_POSITION, position);
                        startActivity(intent);
                    }
                });
            }
        });
        viewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if (throwable != null) {
                    Snackbar.make(coordLayout, "Нет подключения к сети", Snackbar.LENGTH_LONG).show();
                    viewModel.clearErrors();
                }
            }
        });
        viewModel.loadData();

        //View
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setHasFixedSize(true);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onContactSelected(Contacts contacts) {

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}