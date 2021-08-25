package com.example.statophobic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.statophobic.databinding.ActivityInstagramBinding;
import com.example.statophobic.databinding.ActivityLinkDinBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LinkDinActivity extends AppCompatActivity {

    private ActivityLinkDinBinding binding;
    private LinkDinActivity linkDinActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_din);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_link_din);
        linkDinActivity = this;

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLinkDinData();
            }
        });
    }
    private void getLinkDinData() {
        URL url = null;
        try {
            url = new URL(binding.linkDinUrl.getText().toString());

            String host = url.getHost();

            if(host.contains("linkedin.com")){
                new callGetLinkDinData().execute(binding.linkDinUrl.getText().toString());
            }else
                Toast.makeText(linkDinActivity, "Url is invalid !", Toast.LENGTH_SHORT).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
    class callGetLinkDinData extends AsyncTask<String,Void, Document> {
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
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryLinkeDin,linkDinActivity,"LinkDin "+System.currentTimeMillis()+".mp4");
        }
    }
}