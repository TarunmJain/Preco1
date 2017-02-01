package com.sourceedge.preco.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Deekshith on 14-01-2017.
 */

public class PickAndPreview {
    public static void performFileSearch(Context context) {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("application/pdf");
        ((Activity)context).startActivityForResult(intent, Class_Static.READ_REQUEST_CODE);
    }

    public void Preview(){

    }

}
