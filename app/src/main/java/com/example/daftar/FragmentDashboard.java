package com.example.daftar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daftar.model.Customer;
import com.example.daftar.ui.ContactsActivity;
import com.example.daftar.ui.CustomerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentDashboard extends Fragment {

    List<Customer> mCustomersList;

    public FragmentDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mCustomersList = new ArrayList<>();
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        CustomerAdapter mAdapter = new CustomerAdapter();
        mAdapter.setList(mCustomersList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton floatingActionButton = v.findViewById(R.id.add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
