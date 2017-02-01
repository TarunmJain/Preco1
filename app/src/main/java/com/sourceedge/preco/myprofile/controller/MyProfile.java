package com.sourceedge.preco.myprofile.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourceedge.preco.R;
import com.sourceedge.preco.support.Class_Genric;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MyProfile extends AppCompatActivity {
    Toolbar toolbar;
    EditText myprofileName,myprofileEmailid,myprofilePassword,myprofileAge,myprofileGender,myprofilePhoneno,myprofileDept,myprofileYear;
    Button buttonSave;
    TextView selectImage;
    ImageView profilePic;
    String imagestr;
    static String imageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myprofileName=(EditText)findViewById(R.id.myprofile_name);
        myprofileEmailid=(EditText)findViewById(R.id.myprofile_emailid);
        myprofilePassword=(EditText)findViewById(R.id.myprofile_password);
        myprofileAge=(EditText)findViewById(R.id.myprofile_age);
        myprofileGender=(EditText)findViewById(R.id.myprofile_gender);
        myprofilePhoneno=(EditText)findViewById(R.id.myprofile_phoneno);
        myprofileDept=(EditText)findViewById(R.id.myprofile_dept);
        myprofileYear=(EditText)findViewById(R.id.myprofile_year);
        buttonSave=(Button)findViewById(R.id.button_save);
        selectImage=(TextView)findViewById(R.id.select_image);
        profilePic=(ImageView)findViewById(R.id.profile_pic);
        OnClicks();
    }

    private void OnClicks() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    } else Class_Genric.requestCamera(MyProfile.this);
                } else if (options[item].equals("Choose from Gallery")) {
                    if (ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else Class_Genric.requestStorage(MyProfile.this);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    //bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions), 150, 150, true);
                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    String path = Environment.getExternalStorageDirectory() + File.separator + "MyStoreSeller" + File.separator + "Pics";
                    f.delete();
                    OutputStream outFile = null;
                    String filepath = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    File file = new File(path, filepath);
                    String filepic = filepath.substring(filepath.lastIndexOf(".") + 1);
                    try {
                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outFile);
                        outFile.flush();
                        outFile.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    profilePic.setImageBitmap(scaled);
                    imagestr = "data:image/" + filepic + ";" + encodeToBase64(scaled);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                String pic = picturePath.substring(picturePath.lastIndexOf(".") + 1);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //encodeToBase64(thumbnail);
                //Log.w("path of image from gallery......******************.........", picturePath + "");
                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);

                profilePic.setImageBitmap(scaled);
                imagestr = "data:image/" + pic + ";" + encodeToBase64(scaled);

            }
        }
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {                //On Back Arrow pressed
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
