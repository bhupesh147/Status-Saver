package com.example.statophobic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.statophobic.databinding.ActivityShareChatBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShareChatActivity extends AppCompatActivity {
    private ActivityShareChatBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_share_chat);

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getShareChatData();
            }
        });
    }

    private void getShareChatData() {
        try {
            URL url = new URL(binding.shareChatUrl.getText().toString());
            String host = url.getHost();
            if(host.contains("sharechat"))
                new callGetShareChat().execute(binding.shareChatUrl.getText().toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
    class callGetShareChat extends AsyncTask<String,Void, Document>{
        Document shareChatDoc ;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                shareChatDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return shareChatDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");
            if(!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryShareChat,ShareChatActivity.this,
                        "shareChat "+System.currentTimeMillis()+".mp4");
        }
    }
}