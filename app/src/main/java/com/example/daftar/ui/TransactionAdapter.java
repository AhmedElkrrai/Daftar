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


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {
    private List<Transaction> mTransactionList = new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    public void setList(List<Transaction> transactionList) {
        this.mTransactionList = transactionList;
        notifyDataSetChanged();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder {
        public TextView date, note, cash, type;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.transaction_date);
            note = itemView.findViewById(R.id.transaction_note);
            cash = itemView.findViewById(R.id.transaction_cash);
            type = itemView.findViewById(R.id.transaction_type);
        }
    }
}