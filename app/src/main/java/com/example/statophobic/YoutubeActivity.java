package com.example.statophobic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.statophobic.databinding.ActivityInstagramBinding;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeActivity extends AppCompatActivity {
    private ImageView logo;
   // private Button download;
    private String newLink;
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_youtube);

       logo = findViewById(R.id.ytlogo);
      // download = findViewById(R.id.ytdownloadBtn);
       url = findViewById(R.id.youTubeUrl);

       /* binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getYouTubeData();
            }
        });*/
    }

    public void downloadVideo(View view){
        String values = url.getText().toString();

        YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(YoutubeActivity.this){
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if(videoId==null){
                if(ytFiles!=null) {
                    int tag = 22;
                    newLink = ytFiles.get(tag).getUrl();
                    String title = "Your video";
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(newLink));
                    request.setTitle(title);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".mp4");
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    request.allowScanningByMediaScanner();
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                    downloadManager.enqueue(request);
                }
                }
            }
        };
        youTubeUriExtractor.execute(values);

    }


   /* private void getYouTubeData() {
        URL url = null;
        try {
            url = new URL(binding.youTubeUrl.getText().toString());

            String host = url.getHost();

            if(host.contains("youtube.com")){
                new callGetFbData().execute(binding.youTubeUrl.getText().toString());
            }else
                Toast.makeText(youtubeActivity, "Url is invalid !", Toast.LENGTH_SHORT).show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
    class callGetFbData extends AsyncTask<String,Void, Document> {
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
            String videoUrl = document.select("http://www.youtube.com/watch?v=y12-1miZHLs&nomobile=1")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryYouTube,youtubeActivity,"youtube "+System.currentTimeMillis()+".mp4");
        }
    }
}*/

/*import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class YoutubeActivity extends AppCompatActivity {

    private class ReceivingDataFromYoutube extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(YoutubeActivity.this);
        private String result;

        protected void onPreExecute() {
            dialog.setMessage("Downloading...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... arg0) {
            int begin, end;
            String tmpstr = null;
            try {
                URL url=new URL("http://www.youtube.com/watch?v=y12-1miZHLs&nomobile=1");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                InputStream stream=con.getInputStream();
                InputStreamReader reader=new InputStreamReader(stream);
                StringBuffer buffer=new StringBuffer();
                char[] buf=new char[262144];
                int chars_read;
                while ((chars_read = reader.read(buf, 0, 262144)) != -1) {
                    buffer.append(buf, 0, chars_read);
                }
                tmpstr=buffer.toString();

                begin  = tmpstr.indexOf("url_encoded_fmt_stream_map=");
                end = tmpstr.indexOf("&", begin + 27);
                if (end == -1) {
                    end = tmpstr.indexOf("\"", begin + 27);
                }
                tmpstr = Util.URLDecode(tmpstr.substring(begin + 27, end));

            } catch (MalformedURLException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }

            Vector url_encoded_fmt_stream_map = new Vector();
            begin = 0;
            end   = tmpstr.indexOf(",");

            while (end != -1) {
                url_encoded_fmt_stream_map.addElement(tmpstr.substring(begin, end));
                begin = end + 1;
                end   = tmpstr.indexOf(",", begin);
            }

            url_encoded_fmt_stream_map.addElement(tmpstr.substring(begin, tmpstr.length()));
            String result = "";
            Enumeration url_encoded_fmt_stream_map_enum = url_encoded_fmt_stream_map.elements();
            while (url_encoded_fmt_stream_map_enum.hasMoreElements()) {
                tmpstr = (String)url_encoded_fmt_stream_map_enum.nextElement();
                begin = tmpstr.indexOf("itag=");
                if (begin != -1) {
                    end = tmpstr.indexOf("&", begin + 5);

                    if (end == -1) {
                        end = tmpstr.length();
                    }

                    int fmt = Integer.parseInt(tmpstr.substring(begin + 5, end));

                    if (fmt == 35) {
                        begin = tmpstr.indexOf("url=");
                        if (begin != -1) {
                            end = tmpstr.indexOf("&", begin + 4);
                            if (end == -1) {
                                end = tmpstr.length();
                            }
                            result = Util.download(tmpstr.substring(begin + 4, end));
                            this.result=result;
                            break;
                        }
                    }
                }
            }
            try {
                URL u = new URL(result);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
/*              c.setRequestProperty("Youtubedl-no-compression", "True");
              c.setRequestProperty("User-Agent", "YouTube");*/

               /* c.setDoOutput(true);
                c.connect();

                FileOutputStream f=new FileOutputStream(new File("/sdcard/3.flv"));

                InputStream in=c.getInputStream();
                byte[] buffer=new byte[1024];
                int sz = 0;
                while ( (sz = in.read(buffer)) > 0 ) {
                    f.write(buffer,0, sz);
                }
                f.close();
            } catch (MalformedURLException e) {
                new RuntimeException();
            } catch (IOException e) {
                new RuntimeException();
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            dialog.dismiss();
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new ReceivingDataFromYoutube().execute();
    }*/




}