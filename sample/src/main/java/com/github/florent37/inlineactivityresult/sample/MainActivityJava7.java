package com.github.florent37.inlineactivityresult.sample;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.florent37.inlineactivityresult.InlineActivityResult;
import com.github.florent37.inlineactivityresult.Result;
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener;
import com.github.florent37.inlineactivityresult.request.Request;
import com.github.florent37.inlineactivityresult.request.RequestFabric;

public class MainActivityJava7 extends AppCompatActivity {

    private ImageView resultView;
    private View request;
    private View requestIntentSenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        resultView = findViewById(R.id.resultView);
        request = findViewById(R.id.requestView);
        requestIntentSenderView = findViewById(R.id.requestIntentSenderView);

        request.setOnClickListener(view -> myMethod(RequestFabric.create(new Intent(MediaStore.ACTION_IMAGE_CAPTURE))));

        requestIntentSenderView.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

            Request request = RequestFabric.create(pendingIntent.getIntentSender(), null, 0, 0, 0, null);

            myMethod(request);
        });
    }

    private void myMethod(Request request) {
        new InlineActivityResult(this)
                .startForResult(request, new ActivityResultListener() {
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
