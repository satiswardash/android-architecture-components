package com.incture.mobility.architecturecomponents.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by satiswardash on 05/02/18.
 */

public class FileUtility {

    /**
     * Creates a new image file using {@link android.support.v4.content.FileProvider} in External Storage
     *
     * @return
     * @throws IOException
     */
    public File createExternalImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
       //File storageDir = new File(Environment.getDataDirectory().getPath());
        storageDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imageFileName = "file:" + image.getAbsolutePath();
        return image;
    }
}
