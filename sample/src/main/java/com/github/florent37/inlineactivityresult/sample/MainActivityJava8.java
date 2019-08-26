package com.github.florent37.inlineactivityresult.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.inlineactivityresult.InlineActivityResult;

public class MainActivityJava8 extends AppCompatActivity {

    private ImageView resultView;
    private View requestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        resultView = findViewById(R.id.resultView);
        requestView = findViewById(R.id.requestView);

        requestView.setOnClickListener(view -> myMethod());
    }

    private void myMethod(){
        new InlineActivityResult(this)
                .startForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                .onSuccess(result -> {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    resultView.setImageBitmap(imageBitmap);
                })
                .onFail(result -> {

                });
    }
}
