package com.example.kevin.myocrapp120517;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevin on 12/6/2017.
 */

public class MainActivity extends AppCompatActivity {
    Button btn_ocr;
    Button btn_barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_ocr = findViewById(R.id.btn_ocr_text_recognizer);
        btn_barcode = findViewById(R.id.btn_barcode_scanner);

        btn_ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OcrActivity.class);
                startActivity(i);
            }
        });
        btn_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, BarcodeScannerActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
