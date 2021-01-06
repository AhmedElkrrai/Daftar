package com.example.daftar.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.R;
import com.example.daftar.model.Contact;
import com.example.daftar.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NAME;
import static com.example.daftar.ui.ContactsActivity.EXTRA_CUSTOMER_NUMBER;
import static com.example.daftar.ui.TransactionActivity.TRANSACTION_TYPE_GIVEN;

public class FragmentDashboard extends Fragment {

    private CustomerViewModel customerViewModel;

    public static final int ADD_CUSTOMER_REQUEST = 1;
    public static final int UPDATE_CUSTOMER_REQUEST = 2;

    public static final String EXTRA_CUSTOMER_TOTAL_CASH =
            "package com.example.room.EXTRA_CUSTOMER_TOTAL_CASH";
    public static final String EXTRA_TRANSACTION_TYPE =
            "package com.example.room.EXTRA_TRANSACTION_TYPE";
    public static final String EXTRA_ID =
            "package com.example.room.EXTRA_ID";


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
                startActivityForResult(intent, ADD_CUSTOMER_REQUEST);
            }
        });

        mAdapter.setOnItemClickListener(new CustomerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Customer customer) {
                Intent intent = new Intent(view.getContext(), TransactionActivity.class);
                intent.putExtra(EXTRA_CUSTOMER_NAME, customer.getCustomerName());
                intent.putExtra(EXTRA_CUSTOMER_NUMBER, customer.getCustomerNumber());
                intent.putExtra(EXTRA_CUSTOMER_TOTAL_CASH, customer.getTotalCash());
                intent.putExtra(EXTRA_TRANSACTION_TYPE, customer.getDetails());
                intent.putExtra(EXTRA_ID, customer.getId());
                startActivityForResult(intent, UPDATE_CUSTOMER_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CUSTOMER_REQUEST && resultCode == RESULT_OK) {
            String customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);
            String customerNumber = data.getStringExtra(EXTRA_CUSTOMER_NUMBER);
            Customer customer = new Customer(customerName, "0", TRANSACTION_TYPE_GIVEN, customerNumber);
            customerViewModel.insert(customer);
        } else if (requestCode == UPDATE_CUSTOMER_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Noting to update", Toast.LENGTH_SHORT).show();
                return;
            }
            String customerName = data.getStringExtra(EXTRA_CUSTOMER_NAME);
            String customerPhoneNumber = data.getStringExtra(EXTRA_CUSTOMER_NUMBER);
            String totalCash = data.getStringExtra(EXTRA_CUSTOMER_TOTAL_CASH);
            String transactionType = data.getStringExtra(EXTRA_TRANSACTION_TYPE);

            Customer customer = new Customer(customerName, totalCash, transactionType, customerPhoneNumber);
            customer.setId(id);
            customerViewModel.update(customer);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}