package com.example.ytdownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;

import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {
    EditText search;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(view -> {
            String url = search.getText().toString();

            YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(MainActivity.this) {
                @Override
                public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                    if (ytFiles!=null){
                        int tag = 22;
                        String title = "Download";
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ytFiles.get(tag).getUrl()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle(title);
                        request.setDescription("downloading file ...");
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title+".mp4");

                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                }
            };
            youTubeUriExtractor.execute(url);
        });
    }
}