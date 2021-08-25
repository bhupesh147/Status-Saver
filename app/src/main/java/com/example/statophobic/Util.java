package com.example.statophobic;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class Util {
    public static String RootDirectoryFacebook = "/MyStorySaver/Facebook";
    public static String RootDirectoryLinkeDin = "/MyStorySaver/LinkeDin";
    public static String RootDirectoryShareChat = "/MyStorySaver/ShareChat";
    public static String RootDirectoryInstaGram = "/MyStorySaver/InstaGram";
    public static String RootDirectoryYouTube = "/MyStorySaver/YouTube";
    public static File RootDirectoryWhatsApp =
            new File(Environment.getExternalStorageDirectory() +"/Download/MyStorySaver/WhatsApp");


    public static void createFileFolder(){
        if(!RootDirectoryWhatsApp.exists())
            RootDirectoryWhatsApp.mkdirs();
    }

    public static void download(String downloadPath, String destPath, Context context,String fileName){
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse(downloadPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,destPath+fileName);
        ((DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
    }

}
