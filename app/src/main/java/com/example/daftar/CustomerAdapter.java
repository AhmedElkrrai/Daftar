package com.example.daftar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {
    private List<Customer> customers = new ArrayList<>();

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
        holder.customerName.setText(customers.get(position).getCustomerName());
        holder.money.setText(customers.get(position).getMoney());
        holder.details.setText(customers.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void setList(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {
        public TextView customerName;
        public TextView money;
        public TextView details;

        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customer_name);
            money = itemView.findViewById(R.id.money_amount);
            details = itemView.findViewById(R.id.money_details);
        }
    }

    public Customer getCustomerAt(int position) {
        return customers.get(position);
    }

}