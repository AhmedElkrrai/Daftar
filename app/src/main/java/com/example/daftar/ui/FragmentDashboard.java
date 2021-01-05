package com.example.daftar.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.R;
import com.example.daftar.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NUMBER;
import static com.example.daftar.ui.TransactionActivity.TRANSACTION_TYPE_GIVEN;

public class FragmentDashboard extends Fragment {

    private CustomerViewModel customerViewModel;
    private static final String TAG = "FragmentDashboard";
    public static final int ADD_Customer_REQUEST = 1;

    public FragmentDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        CustomerAdapter mAdapter = new CustomerAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(getActivity(), new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                mAdapter.setList(customers);
            }
        });

        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                startActivityForResult(intent, ADD_Customer_REQUEST);
            }
        });

        mAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Customer customer) {
                Intent intent = new Intent(view.getContext(), TransactionActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_NAME, customer.getCustomerName());
                intent.putExtra(EXTRA_CUSTOMER_NUMBER, customer.getCustomerNumber());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_Customer_REQUEST) {
            String customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);
            String customerNumber = data.getStringExtra(EXTRA_CUSTOMER_NUMBER);
            Customer customer = new Customer(customerName, "0", TRANSACTION_TYPE_GIVEN, customerNumber);
            customerViewModel.insert(customer);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
