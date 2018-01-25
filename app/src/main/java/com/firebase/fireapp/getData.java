package com.firebase.fireapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getData extends AppCompatActivity {

    public Button getmydata;
    public ImageView postimg;
    public ImageView profilepic;
    public TextView userName;
    public TextView titletxt;
    public TextView des;
    public TextView uc;
    public TextView dc;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myref = FirebaseDatabase.getInstance().getReference();
        profilepic = (ImageView)findViewById(R.id.profilepic);
        postimg = (ImageView)findViewById(R.id.postpic);
        userName = (TextView)findViewById(R.id.userName);
        titletxt = (TextView)findViewById(R.id.title);
        des = (TextView)findViewById(R.id.description);
        uc = (TextView)findViewById(R.id.uc);
        dc = (TextView)findViewById(R.id.dc);
        getmydata = (Button)findViewById(R.id.getData);
        getmydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

    }

    public void open()
    {
        DatabaseReference mychild = myref.child("milan").child("profilepic");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                Glide.with(getApplicationContext()).load(s).into(profilepic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       mychild = myref.child("milan").child("userName");
       mychild.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String s = dataSnapshot.getValue().toString();
               userName.setText(s);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
        mychild = myref.child("milan").child("post").child("post1").child("title");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                titletxt.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mychild = myref.child("milan").child("post").child("post1").child("description");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                des.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mychild = myref.child("milan").child("post").child("post1").child("imgUrl");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                Glide.with(getApplicationContext()).load(s).into(postimg);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mychild = myref.child("milan").child("post").child("post1").child("upCount");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                uc.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mychild = myref.child("milan").child("post").child("post1").child("downCount");
        mychild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue().toString();
                dc.setText(s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
