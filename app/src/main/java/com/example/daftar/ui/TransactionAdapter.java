package com.example.daftar.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.R;
import com.example.daftar.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import static com.example.daftar.ui.FragmentDashboard.GREEN;
import static com.example.daftar.ui.FragmentDashboard.RED;
import static com.example.daftar.ui.TransactionActivity.TRANSACTION_TYPE_GIVEN;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {
    private List<Transaction> mTransactionList = new ArrayList<>();
    private String customerName;

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        holder.date.setText(mTransactionList.get(position).getDate());
        holder.note.setText(mTransactionList.get(position).getNote());
        holder.cash.setText(mTransactionList.get(position).getCash());
        holder.type.setText(mTransactionList.get(position).getType());
        if (holder.type.getText().toString().equals(TRANSACTION_TYPE_GIVEN)) {
            holder.cash.setTextColor(RED);
            holder.moneyUnit.setTextColor(RED);
        } else {
            holder.cash.setTextColor(GREEN);
            holder.moneyUnit.setTextColor(GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public void setList(List<Transaction> transactionList) {
        List<Transaction> customerTransactionList = new ArrayList<>();
        for (int i = transactionList.size() - 1; i > 0; i--) {
            if (transactionList.get(i).getCustomerName().equals(customerName))
                customerTransactionList.add(transactionList.get(i));
        }
        this.mTransactionList = customerTransactionList;
        notifyDataSetChanged();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder {
        public TextView date, note, cash, type, moneyUnit;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.transaction_date);
            note = itemView.findViewById(R.id.transaction_note);
            cash = itemView.findViewById(R.id.transaction_cash);
            type = itemView.findViewById(R.id.transaction_type);
            moneyUnit = itemView.findViewById(R.id.money_unit);
        }
    }
}