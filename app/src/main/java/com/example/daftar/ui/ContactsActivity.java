package com.example.daftar.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.daftar.R;
import com.example.daftar.model.Contact;
import com.example.daftar.model.Customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactsActivity extends AppCompatActivity {

    private final int READ_CONTACTS_CODE = 12;
    private List<Contact> mContactsList;
    private ContactAdapter mAdapter;

    public static final String EXTRA_CUSTOMER_NAME =
            "package com.example.room.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_CUSTOMER_NUMBER =
            "package com.example.room.EXTRA_CUSTOMER_NUMBER";


    private static final String TAG = "ContactsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        requestPermission();

        RecyclerView mRecyclerView = findViewById(R.id.contacts_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mContactsList = new ArrayList<>();

        mAdapter = new ContactAdapter();
        mAdapter.setList(mContactsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(Contact contact) {
                String customerName = contact.getContactName();
                Toast.makeText(ContactsActivity.this, "Customer selected", Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                data.putExtra(EXTRA_CUSTOMER_NAME, customerName);
                data.putExtra(EXTRA_CUSTOMER_NUMBER, contact.getPhoneNumber());

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED)
            loadContacts();
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Contact contact;
        Cursor cursor
                = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber
                        = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor cursor2
                            = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            , new String[]{id}, null);

                    Set<String> hashSet = new HashSet<>();

                    while (cursor2.moveToNext()) {
                        String phoneNumber
                                = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneNumber = phoneNumber.replace(" ", "");
                        if (!hashSet.contains(phoneNumber)) {
                            contact = new Contact(name, phoneNumber);
                            mContactsList.add(contact);
                            mAdapter.notifyDataSetChanged();
                            hashSet.add(phoneNumber);
                        }
                    }
                    cursor2.close();
                }
            }
        }
        cursor.close();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionUTL();
        }
    }

    private void requestPermissionUTL() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ContactsActivity.this, Manifest.permission.READ_CONTACTS)) {
            new AlertDialog.Builder(ContactsActivity.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(ContactsActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(ContactsActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_CODE);
        }
    }


}