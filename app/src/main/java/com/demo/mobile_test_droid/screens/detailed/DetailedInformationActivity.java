package com.demo.mobile_test_droid.screens.detailed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.TextView;
import com.demo.mobile_test_droid.R;
import com.demo.mobile_test_droid.pojo.Contacts;
import com.demo.mobile_test_droid.screens.search_contacts.ContactsViewModel;

import java.util.List;

public class DetailedInformationActivity extends AppCompatActivity {

    private ContactsViewModel viewModel;
    private TextView name;
    private TextView phone;
    private TextView temperament;
    private TextView biography;
    private TextView educationPeriod;
    private int position;
    public static final String EXTRA_CONTACT_POSITION = "position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_information);
        position = getIntent().getIntExtra(EXTRA_CONTACT_POSITION, 0);
        name = (TextView) findViewById(R.id.textViewNameDetails);
        phone = (TextView) findViewById(R.id.textViewPhoneDetails);
        temperament = (TextView) findViewById(R.id.textViewTemperament);
        biography = (TextView) findViewById(R.id.textViewBiography);
        educationPeriod = (TextView) findViewById(R.id.textViewDateStartEnd);

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contact) {
                name.setText(contact.get(position).getName());
                phone.setText(contact.get(position).getPhone());
                String text = contact.get(position).getTemperament();
                StringBuilder builder = new StringBuilder(text);
                if (Character.isAlphabetic(text.codePointAt(0)))
                    builder.setCharAt(0, Character.toUpperCase(text.charAt(0)));
                temperament.setText(builder);
                biography.setText(contact.get(position).getBiography());
                String period = contact.get(position).getEducationPeriod().getStart() + " - " + contact.get(position).getEducationPeriod().getEnd();
                educationPeriod.setText(period);
            }
        });
    }
}