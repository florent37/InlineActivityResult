package com.github.florent37.inlineactivityresult.sample.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.github.florent37.inlineactivityresult.InlineActivityResult;
import com.github.florent37.inlineactivityresult.request.Request;
import com.github.florent37.inlineactivityresult.Result;
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener;
import com.github.florent37.inlineactivityresult.request.RequestFabric;
import com.github.florent37.inlineactivityresult.sample.R;

public class MainFragmentJava7 extends Fragment {

    private ImageView resultView;
    private View request;
    private View requestIntentSenderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_request, null);

        resultView = root.findViewById(R.id.resultView);
        request = root.findViewById(R.id.requestView);
        requestIntentSenderView = root.findViewById(R.id.requestIntentSenderView);

        request.setOnClickListener(view -> myMethod(RequestFabric.create(new Intent(MediaStore.ACTION_IMAGE_CAPTURE))));

        requestIntentSenderView.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

            Request request = RequestFabric.create(pendingIntent.getIntentSender(), null, 0, 0, 0, null);

            myMethod(request);
        });

        return root;
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
