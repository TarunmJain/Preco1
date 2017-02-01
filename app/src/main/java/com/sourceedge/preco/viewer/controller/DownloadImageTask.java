package com.sourceedge.preco.viewer.controller;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Centura User3 on 1/19/2017.
 */

public class DownloadImageTask extends AsyncTask {

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        PdfViewer.Continue();

    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        Toast.makeText(PdfViewer.context,values+"",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected Object doInBackground(Object[] params) {
        try {
            PdfViewer.input = new URL("http://www.pdf995.com/samples/pdf.pdf").openStream();
            //PdfViewer.input = new URL("https://www.wpi.edu/sites/default/files/docs/Events/Annual-Events/landscape.pdf").openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
