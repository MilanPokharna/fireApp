package com.firebase.fireapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int gallery_Intent = 2;
    public static final int carema_intent = 1;
    public DatabaseReference imgref;
    public Button shw;
    public Uri fileUri;
    public Button but;
    public Button but2;
    public Button but3;
    public Button but4;
    public Button cap;
    public static int flag=1;
    public ImageView img;
    public EditText text1;
    public EditText text2;
    public TextView text3;
    public TextView text4;
    public Button getdatafast;
    public StorageReference mstorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myref = database.getReference();
        mstorageRef = FirebaseStorage.getInstance().getReference();
        but2 = (Button) findViewById(R.id.button2);
        text3 = (TextView) findViewById(R.id.Text3);
        text4 = (TextView) findViewById(R.id.Text4);
        shw = (Button) findViewById(R.id.show);
        but3 = (Button)findViewById(R.id.button3);
        getdatafast = (Button)findViewById(R.id.getData);
        but4 = (Button)findViewById(R.id.button4);
        img = (ImageView) findViewById(R.id.imageView);
        but = (Button) findViewById(R.id.button);
        text1 = (EditText) findViewById(R.id.Text1);
        text2 = (EditText) findViewById(R.id.Text2);
        cap = (Button)findViewById(R.id.capture);

        getdatafast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openData();

            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = text1.getText().toString();
                String data2 = text2.getText().toString();
                DatabaseReference mchild = myref.child(data1);
                mchild.setValue(data2);
            }

        });
        but2 = (Button) findViewById(R.id.button2);
        text3 = (TextView) findViewById(R.id.Text3);
        text4 = (TextView) findViewById(R.id.Text4);

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data1 = text1.getText().toString();
                DatabaseReference mchild = myref.child(data1);
                mchild.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val = dataSnapshot.getValue().toString();
                        text3.setText(val);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                Intent getpic = new Intent(Intent.ACTION_PICK);
                getpic.setType("image/*");
                startActivityForResult(getpic,gallery_Intent );
            }
        });

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,carema_intent );
            }
        });

        shw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference mchild = myref.child("milan").child("post").child("post1").child("imgUrl");

                mchild.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String ss = dataSnapshot.getValue().toString();
                        Glide.with(getApplicationContext()).load(ss).into(img);
                         }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
    public void openData() {
        Intent intent = new Intent(MainActivity.this,getData.class);
        startActivity(intent);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == gallery_Intent )
            {
                final Uri uri = data.getData();
                try {
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img.setVisibility(View.VISIBLE
                    );
                    img.setImageBitmap(selectedImage);
                    but4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            StorageReference filePath = mstorageRef.child("photos").child(uri.getLastPathSegment());
                            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(MainActivity.this, "Uplaod Done", Toast.LENGTH_SHORT).show();
                                    imgref = FirebaseDatabase.getInstance().getReference();
                                    imgref.child("img").setValue(taskSnapshot.getDownloadUrl().toString());
                                }
                            });
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode != RESULT_CANCELED)
            {
                if (requestCode == carema_intent) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    img.setImageBitmap(photo);


//                    
                }
            }
        }
//            else if (requestCode == carema_intent) {
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                final Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath(),options);
//                img.setVisibility(View.VISIBLE);
//                img.setImageBitmap(bmp);
//                StorageReference filePath = mstorageRef.child("photos").child(fileUri.getLastPathSegment());
//                filePath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(MainActivity.this, "Uplaod Done", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                    }

                }
            }

