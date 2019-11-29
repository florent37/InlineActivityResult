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

import com.github.florent37.inlineactivityresult.Result;
import com.github.florent37.inlineactivityresult.request.Request;
import com.github.florent37.inlineactivityresult.request.RequestFabric;
import com.github.florent37.inlineactivityresult.rx.RxInlineActivityResult;
import com.github.florent37.inlineactivityresult.sample.R;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

public class MainFragmentRx extends Fragment {

    private ImageView resultView;
    private View requestView;
    private View requestIntentSenderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_request, null);

        resultView = root.findViewById(R.id.resultView);
        requestView = root.findViewById(R.id.requestView);
        requestIntentSenderView = root.findViewById(R.id.requestIntentSenderView);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        setOnClick(requestView, $ -> new RxInlineActivityResult(this).request(intent));


        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        setOnClick(requestIntentSenderView, $ -> {
            Request request = RequestFabric.create(pendingIntent.getIntentSender(), null, 0, 0, 0, null);

            return new RxInlineActivityResult(this).request(request);
        });

        return root;
    }

    private void setOnClick(View view, Function<Object, ObservableSource<? extends Result>> mapper) {
        RxView.clicks(view)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(mapper)
                .subscribe(result -> {
                    Bundle extras = result.getData().getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    resultView.setImageBitmap(imageBitmap);
                }, throwable -> {
                    if (throwable instanceof RxInlineActivityResult.Error) {
                        final Result result = ((RxInlineActivityResult.Error) throwable).getResult();


                    }
                });
    }
}
