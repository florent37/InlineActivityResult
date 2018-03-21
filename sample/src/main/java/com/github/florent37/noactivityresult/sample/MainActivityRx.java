package com.github.florent37.noactivityresult.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.florent37.noactivityresult.Result;
import com.github.florent37.noactivityresult.rx.RxNoActivityResult;
import com.github.florent37.noactivityresult.sample.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivityRx extends AppCompatActivity {

    private ImageView resultView;
    private View requestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        resultView = findViewById(R.id.resultView);
        requestView = findViewById(R.id.requestView);

        RxView.clicks(requestView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap($ -> new RxNoActivityResult(this).request(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)))
                .subscribe(result -> {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    resultView.setImageBitmap(imageBitmap);
                }, throwable -> {
                    if (throwable instanceof RxNoActivityResult.Error) {
                        final Result result = ((RxNoActivityResult.Error) throwable).getResult();


                    }
                });
    }
}
