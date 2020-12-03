package com.demo.mobile_test_droid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.mobile_test_droid.R;
import com.demo.mobile_test_droid.pojo.Contacts;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ExampleViewHolder> implements Filterable {

    private Context context;
    private List<Contacts> contactsList;
    private Listener listener;
    private ContactsAdapterListener listener2;
    private List<Contacts> contactListFiltered;

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public ContactsAdapter(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        holder.textViewName.setText(String.valueOf(contactsList.get(position).getName()));
        holder.textViewPhone.setText(String.valueOf(contactsList.get(position).getPhone()));
        holder.textViewHeight.setText(String.valueOf(contactsList.get(position).getHeight()));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textViewName;
        private TextView textViewPhone;
        private TextView textViewHeight;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewHeight = itemView.findViewById(R.id.textViewHeight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactsList;
                } else {
                    List<Contacts> filteredList = new ArrayList<>();
                    for (Contacts row : contactsList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Contacts>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public interface ContactsAdapterListener {
        void onContactSelected(Contacts contacts);
    }
}
