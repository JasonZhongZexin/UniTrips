/**
 * Copyright (c) 2018. [ Zexin Zhong ]
 *
 */

package com.sep.UniTrips.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sep.UniTrips.R;

public class Database_tester extends AppCompatActivity {

    private EditText courseEt;
    private EditText transportEt;
    private Button submitBtn;
    private Button readBtn;
    private FirebaseAuth mAuth;
    private TextView textView;
    String text;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_tester);
        courseEt = findViewById(R.id.course_detail_Et);
        transportEt = findViewById(R.id.transportSettingEt);
        submitBtn = findViewById(R.id.submit_tester_btn);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        readBtn = findViewById(R.id.readBtn);
        textView = findViewById(R.id.textpart);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("current user provide id is "+currentUser.getProviderId());
                mDatabase.child("users").child(currentUser.getUid()).child("course detail").setValue(courseEt.getText().toString());
                mDatabase.child("users").child(currentUser.getUid()).child("transport detail").setValue(transportEt.getText().toString());
                Toast.makeText(Database_tester.this, "button clicked",Toast.LENGTH_LONG).show();
                Log.d("submit button clicked","button clicked");
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text="2222";
                mDatabase.child("users").child(currentUser.getUid()).child("course detail").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        text += dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mDatabase.child("users").child(currentUser.getUid()).child("transport detail").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        text += dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                textView.setText(text);
            }
        });
    }
}
