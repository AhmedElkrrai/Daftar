package com.example.daftar.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.model.Customer;
import com.example.daftar.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> implements Filterable {
    private List<Customer> mCustomersList = new ArrayList<>();
    private List<Customer> mSearchListFull;
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
        mSearchListFull = new ArrayList<>(customers);
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

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
                filteredList.addAll(mSearchListFull);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Customer customer : mSearchListFull) {
                    if (customer.getCustomerName().toLowerCase().contains(filterPattern))
                        filteredList.add(customer);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCustomersList.clear();
            mCustomersList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}