package com.example.statophobic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.statophobic.databinding.ActivityFaceBookBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FaceBookActivity extends AppCompatActivity {
    private ActivityFaceBookBinding binding;
    private FaceBookActivity fbActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_face_book);
       fbActivity = this;

       binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getFacebookData();
           }
       });
    }

    private void getFacebookData() {
        URL url = null;
        try {
            url = new URL(binding.fbUrl.getText().toString());

            String host = url.getHost();

            if(host.contains("facebook.com")){
                new callGetFbData().execute(binding.fbUrl.getText().toString());
            }else
                Toast.makeText(fbActivity, "Url is invalid !", Toast.LENGTH_SHORT).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
    class callGetFbData extends AsyncTask<String,Void, Document>{
        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryFacebook,fbActivity,"facebook "+System.currentTimeMillis()+".mp4");
        }
    }
}