package com.github.florent37.inlineactivityresult.request;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class RequestFabric {

    private RequestFabric() {

    }

    public static Request create(Intent intent) {
        return new RequestActivityForResult(intent);
    }

    public static Request create(IntentSender intentSender,
                                 @Nullable Intent fillInIntent,
                                 int flagsMask,
                                 int flagsValues,
                                 int extraFlags,
                                 @Nullable Bundle options) {
        return new RequestIntentSender(intentSender, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

}
