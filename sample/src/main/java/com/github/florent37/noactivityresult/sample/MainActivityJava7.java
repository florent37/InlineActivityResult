package com.github.florent37.noactivityresult.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.florent37.noactivityresult.NoActivityResult;
import com.github.florent37.noactivityresult.Result;
import com.github.florent37.noactivityresult.callbacks.ActivityResultListener;

public class MainActivityJava7 extends AppCompatActivity {

    private ImageView resultView;
    private View request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        resultView = findViewById(R.id.resultView);
        request = findViewById(R.id.requestView);

        request.setOnClickListener(view -> myMethod());
    }

    private void myMethod() {
        new NoActivityResult(this)
                .startForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), new ActivityResultListener() {
                    @Override
                    public void onSuccess(Result result) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        resultView.setImageBitmap(imageBitmap);
                    }

                    @Override
                    public void onFailed(Result result) {

                    }
                });
    }
}
