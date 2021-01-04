package com.example.daftar.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.model.Customer;
import com.example.daftar.R;

import java.util.ArrayList;
import java.util.List;


public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {
    private List<Customer> mCustomersList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
        holder.customerName.setText(mCustomersList.get(position).getCustomerName());
        holder.money.setText(mCustomersList.get(position).getTotalCash());
        holder.details.setText(mCustomersList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return mCustomersList.size();
    }

    public void setList(List<Customer> customers) {
        this.mCustomersList = customers;
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION)
                            listener.onItemClicked(mCustomersList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Customer customer);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Customer getCustomerAt(int position) {
        return mCustomersList.get(position);
    }

}