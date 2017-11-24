package com.example.hp.zha.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.zha.Adapters.photoAdap;
import com.example.hp.zha.Adapters.postAdap;
import com.example.hp.zha.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HP on 11/17/2017.
 */

public class create_photo extends Fragment {
    ImageView ig1, ig2, imageView;
    public EditText des;
    FloatingActionButton send;
    CharSequence[] items = {"Take Photo", "Choose from library", "Cancel"};
    public static final int PICK_IMAGE = 1;
    public static final int RESULT_LOAD_IMAGE = 2;
    Uri imageData,imageUri;
    public Bitmap bitmap;
    public String picturePath;
    Uri yourUri;
    public Firebase fb;
    public String url="https://zhap-66ed5.firebaseio.com/";
    public StorageReference fb_stg;
    public ProgressDialog progressDialog;

    public create_photo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view1;
        view1 = inflater.inflate(R.layout.create_photos, container, false);
        ig1 = (ImageView) view1.findViewById(R.id.imageButton);
        imageView=(ImageView)view1.findViewById(R.id.select);
        des=(EditText)view1.findViewById(R.id.description);
        send=(FloatingActionButton)view1.findViewById(R.id.floatingActionButton3);
        Firebase.setAndroidContext(view1.getContext());
        fb=new Firebase(url);
        ig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new photos()).commit();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose image");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items[i].equals("Take Photo")) {
                            String fileName = "new-photo-name.jpg";
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, fileName);
                            values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                            //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                            imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE);
                            Toast.makeText(view.getContext(), "Say Cheese", Toast.LENGTH_LONG).show();
                        }
                        if (items[i].equals("Choose from library")) {
                            bitmap = null;
                            imageData = null;
                            Intent it = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(it, RESULT_LOAD_IMAGE);
                            Toast.makeText(view.getContext(), "Select One Picture", Toast.LENGTH_LONG).show();


                        }
                        if (items[i].equals("Cancel")) {
                            Toast.makeText(view.getContext(), "Go Back To Redo Action", Toast.LENGTH_LONG).show();

                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        return view1;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                bitmap = (Bitmap) data.getExtras().get("data");
                imageData = imageUri;
               imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                imageView.setImageBitmap(bitmap);
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    imageData = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(imageData, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);


                    yourUri = Uri.parse(picturePath);


                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageData);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    public class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Creating event...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            fb_stg= FirebaseStorage.getInstance().getReference();
            Calendar c =Calendar.getInstance();
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat ssf=new SimpleDateFormat("HH:mm");
            final String date=sdf.format(c.getTime());
            final String time=ssf.format(c.getTime());
            System.out.println("ammo"+time);
            final String posttitle=des.getText().toString();

            StorageReference stg=fb_stg.child("Photo").child(date+"@"+time+"@"+posttitle);
            stg.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri=taskSnapshot.getDownloadUrl();
                    String downloadurl=downloaduri.toString();
                    photoAdap photoadap=new photoAdap();
                    photoadap.setPurl(downloadurl);
                    photoadap.setTitle(posttitle);
                    fb.child("Photo").child(date+"@"+time+"@"+posttitle).setValue(photoadap);
                    progressDialog.dismiss();
                    getFragmentManager().beginTransaction().replace(R.id.frame_container,new photos()).commit();
                }
            });

            return null;
        }

    }
}

