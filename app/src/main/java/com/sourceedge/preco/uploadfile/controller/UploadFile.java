package com.sourceedge.preco.uploadfile.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.internal.Utility;
import com.sourceedge.preco.R;
import com.sourceedge.preco.homescreen.controller.HomeScreen;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.Class_Static;
import com.sourceedge.preco.support.PickAndPreview;
import com.sourceedge.preco.viewer.controller.PdfViewer;
import com.sourceedge.preco.viewer.controller.Viewer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadFile extends AppCompatActivity implements PrintHelper.OnPrintFinishCallback {
    Toolbar toolbar;
    LinearLayout selectimage, selectdoc, clickimage;
    SharedPreferences sharedPreferences;
    public static Uri userPickedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class_Genric.checkPermission(UploadFile.this);
        setContentView(R.layout.activity_upload_file);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Upload File");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectimage = (LinearLayout) findViewById(R.id.selectimage);
        selectdoc = (LinearLayout) findViewById(R.id.selectdoc);
        clickimage = (LinearLayout) findViewById(R.id.clickimage);
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, MODE_PRIVATE);
        OnClicks();
    }

    private void OnClicks() {
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
        });

        selectdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("*/*");
                //startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICKFILE_RESULT_CODE);
                //startActivity(new Intent(UploadFile.this, PrintCustomContent.class));
                PickAndPreview.performFileSearch(UploadFile.this);
            }
        });

        clickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Class_Static.ispdf = false;
            if (requestCode == 1) {
                Class_Static.ispdf = false;
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

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    doPhotoPrint(UploadFile.this, bitmap);

                    //viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //startActivity(new Intent(UploadFile.this, PrintProperties.class));

            } else if (requestCode == 2) {
                Class_Static.ispdf = false;
                Uri selectedImage = data.getData();
                //preview(UploadFile.this,data);
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                doPhotoPrint(UploadFile.this, thumbnail);

                // startActivity(new Intent(UploadFile.this, PrintProperties.class));
                // Log.w("path of image from gallery......******************.........", picturePath+"");
                // viewImage.setImageBitmap(thumbnail);
            } else {
                Class_Static.ispdf = true;
                if (resultCode == Activity.RESULT_OK) {
                    userPickedUri = data.getData();
                    //new File(userPickedUri.getPath());
                    File myFile = new File(userPickedUri.getPath());
                    String path=myFile.getAbsolutePath();
                    SharedPreferences.Editor edit=sharedPreferences.edit();
                    edit.putString(Class_Genric.Sp_Pdf, userPickedUri.getPath());
                    edit.commit();
                    // showFileInfo(userPickedUri);
                }
                startActivity(new Intent(UploadFile.this, PdfViewer.class));

                /*if (requestCode == Class_Static.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                    preview(UploadFile.this, data);
                }*/
            }
        }
    }

    private void doPhotoPrint(Context context, Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        SharedPreferences.Editor edit=sharedPreferences.edit();
        String saveThis = Base64.encodeToString(byteArray, Base64.DEFAULT);
        edit.putString(Class_Genric.Sp_Image, saveThis);
        edit.commit();

        Intent i = new Intent(UploadFile.this, Viewer.class);
        //i.putExtra("Image", byteArray);
        startActivity(i);

            /*PrintHelper photoPrinter = new PrintHelper(context);
            photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            photoPrinter.printBitmap("preco", bitmap);*/


        //context.startActivity(new Intent(context,PrintProperties.class));
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onFinish() {

    }
}
