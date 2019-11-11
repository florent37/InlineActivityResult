package com.github.florent37.inlineactivityresult.request;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Fabric for create {@link Request} objects.
 */
public class RequestFabric {

    private RequestFabric() {

    }

    /**
     * Create request for call {@link android.app.Activity#startActivityForResult(Intent, int, Bundle)}.
     */
    public static Request create(Intent intent) {
        return new RequestActivityForResult(intent, null);
    }

    /**
     * Create request for call {@link android.app.Activity#startActivityForResult(Intent, int, Bundle)}.
     */
    public static Request create(Intent intent, @Nullable Bundle options) {
        return new RequestActivityForResult(intent, options);
    }

    /**
     * Create request for call {@link android.app.Activity#startIntentSenderForResult(IntentSender, int, Intent, int, int, int, Bundle)}.
     */
    public static Request create(IntentSender intentSender,
                                 @Nullable Intent fillInIntent,
                                 int flagsMask,
                                 int flagsValues,
                                 int extraFlags,
                                 @Nullable Bundle options) {
        return new RequestIntentSender(intentSender, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

}
