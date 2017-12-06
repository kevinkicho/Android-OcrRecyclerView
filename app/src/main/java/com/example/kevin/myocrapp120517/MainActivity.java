package com.example.kevin.myocrapp120517;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionId = 1001;
    String read_text;

    FloatingActionButton fab;

    List<ListItem> item_list;

    Button btn;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionId:
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (SurfaceView)findViewById(R.id.surface_view);
        textView = (TextView)findViewById(R.id.text_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        btn = (Button)findViewById(R.id.button_recycler_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplication()).build();
        if(!textRecognizer.isOperational()){
            Log.w("MainActivity","Detector dependencies are not yet available.");
        }
        else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try{
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionId);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0){
                        textView.post(new Runnable(){
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i<items.size(); ++i){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                read_text = stringBuilder.toString();
                                textView.setText(read_text);
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(read_text != null){
                    ListItem item = new ListItem(read_text);
                    item_list.add(item);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) item_list);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
