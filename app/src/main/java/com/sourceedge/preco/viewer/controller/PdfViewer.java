package com.sourceedge.preco.viewer.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.sourceedge.preco.R;
import com.sourceedge.preco.payment.controller.Payments;
import com.sourceedge.preco.printproperties.controller.PrintProperties;
import com.sourceedge.preco.support.Class_Genric;
import com.sourceedge.preco.timeslots.controller.SlotSelection;
import com.sourceedge.preco.uploadfile.controller.UploadFile;

import java.io.InputStream;

public class PdfViewer extends AppCompatActivity implements OnPageChangeListener,OnPageScrollListener {
    static PDFView pdfView;
    SharedPreferences sharedPreferences;
    TextView next,edit;
    public static Context context;
    public static InputStream input;
    ImageView incrementCopies,decrementCopies;
    TextView copies;
    Integer pageNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Preview");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences=this.getSharedPreferences(Class_Genric.MyPref,MODE_PRIVATE);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        incrementCopies = (ImageView) findViewById(R.id.incrementcopies);
        decrementCopies = (ImageView) findViewById(R.id.decrementcopies);
        copies = (TextView) findViewById(R.id.copies);
        next=(TextView)findViewById(R.id.next);
        edit=(TextView)findViewById(R.id.edit);
        pdfView.useBestQuality(true);
        context= PdfViewer.this;
        Class_Genric.ShowDialog(PdfViewer.this,"Please Wait...",true);
        pdfView.fromUri(UploadFile.userPickedUri)
                .defaultPage(0)
                .onPageChange(this)
                .swipeHorizontal(true)
                .enableDoubletap(false)
                .onPageScroll(this)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Class_Genric.ShowDialog(context,"Please Wait...",false);
                        //Toast.makeText(PdfActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
                    }
                }).load();
        //new DownloadImageTask().execute("");

        OnClicks();
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (UploadFile.userPickedUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(UploadFile.userPickedUri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = UploadFile.userPickedUri.getLastPathSegment();
        }
        return result;
    }

    public static void Continue(){
        pdfView.fromStream(input)
                .defaultPage(1)
                .swipeHorizontal(true)
                .enableDoubletap(false)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Class_Genric.ShowDialog(context,"Please Wait...",false);
                        //Toast.makeText(PdfActivity.this, String.valueOf(nbPages), Toast.LENGTH_LONG).show();
                    }
                }).load();
    }
    private void OnClicks() {

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PdfViewer.this, Payments.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PdfViewer.this, PrintProperties.class));
                finish();
            }
        });

        incrementCopies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copies.setText((Integer.parseInt(copies.getText().toString()) + 1) + "");
            }
        });

        decrementCopies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(copies.getText().toString().trim())) {
                    copies.setText("1");
                }
                if (!copies.getText().toString().matches("1")) {
                    copies.setText((Integer.parseInt(copies.getText().toString()) - 1) + "");
                    //productStock.setText("Stock Available : " + ((Integer.parseInt(productStock.getText().toString().split(": ")[1]) + 1) + ""));
                } else
                    Toast.makeText(PdfViewer.this, "Min Count is 1", Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        //Toast.makeText(PdfViewer.this,pageNumber.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {

    }
}


