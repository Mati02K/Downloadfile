package com.example.downloadfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE=1000;
    Button downBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downBtn = (Button)findViewById(R.id.down);

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else
                    {
                        startDownloading();
                    }
                }
                else
                {
                    startDownloading();
                }


            }
        });
    }
    private void startDownloading()
    {
        String MANVAASAMID="30122020MAN22";
      String URL = new StringBuilder().append("http://192.168.0.5/manvaasam/invoice.php?manid=").append(MANVAASAMID).toString();

      DownloadManager.Request request=new DownloadManager.Request(Uri.parse(URL));
      request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
      request.setTitle("Invoice"+MANVAASAMID);
      request.setDescription("Downloading Invoice..");

//      request.allowScanningByMediaScanner();
      request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
      request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis());

      DownloadManager manager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
      manager.enqueue(request);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startDownloading();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Permission Denied..",Toast.LENGTH_LONG).show();
                }
        }

    }
}