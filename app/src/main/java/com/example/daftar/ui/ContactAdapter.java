package com.example.daftar.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.R;
import com.example.daftar.model.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private List<Contact> mContactsList = new ArrayList<>();

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.contact_name_TV.setText(mContactsList.get(position).getContactName());
        holder.contact_phone_num_TV.setText(mContactsList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    public void setList(List<Contact> contactList) {
        this.mContactsList = contactList;
        notifyDataSetChanged();
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        public TextView contact_name_TV, contact_phone_num_TV;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            contact_name_TV = itemView.findViewById(R.id.contact_name);
            contact_phone_num_TV = itemView.findViewById(R.id.phone_num);
        }
    }
}