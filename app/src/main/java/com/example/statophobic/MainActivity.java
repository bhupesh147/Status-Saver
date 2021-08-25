package com.example.statophobic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.webkit.PermissionRequest;

import com.example.statophobic.databinding.ActivityMainBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.WhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WhatsAppActivity.class));
            }
        });
        binding.FaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FaceBookActivity.class));
            }
        });
        binding.ShareChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ShareChatActivity.class));
            }
        });
        binding.Instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InstagramActivity.class));
            }
        });
        binding.YouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,YoutubeActivity.class));
            }
        });
        binding.LinkDin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LinkDinActivity.class));
            }
        });

        checkPermission();
    }

      private void checkPermission(){
          Dexter.withContext(this)
                  .withPermissions(
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.WRITE_EXTERNAL_STORAGE
                  ).withListener(new MultiplePermissionsListener() {
              @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                  if(!report.areAllPermissionsGranted())
                      checkPermission();
              }

              @Override
              public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken token) {

              }
          }).check();
      }
}











































/*  if(!checkPermission()){
            showPermissionDialog();
        }*/
    /*private void showPermissionDialog(){
       if(SDK_INT >= Build.VERSION_CODES.R){
           try {
               Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
               intent.addCategory("android.intent.category.DEFAULT");
               intent.setData(Uri.parse(String.format("package:%s",new Object[]{getApplicationContext().getPackageName()})));
               startActivityForResult(intent,2000);
           } catch (Exception e) {
               Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
               startActivityForResult(intent,2000);
           }
       }else
           ActivityCompat.requestPermissions(MainActivity.this,
                   new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},333);
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();

        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(),
                    WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE);
            return write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==333){
            if(grantResults.length>0){
                       boolean write =grantResults[0]== PackageManager.PERMISSION_GRANTED;
                       boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                       if (read && write){

                       }else{

                       }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2000){
            if(SDK_INT>=Build.VERSION_CODES.R){
                if(Environment.isExternalStorageManager()){

                }else{

                }
            }
        }
    }*/