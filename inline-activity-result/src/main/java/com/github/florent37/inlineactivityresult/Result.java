package com.github.florent37.inlineactivityresult;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Result {
    @NonNull
    private final InlineActivityResult inlineActivityResult;

    private final int requestCode;
    private final int resultCode;

    @Nullable
    private final Intent data;

    @Nullable
    private Throwable cause;

    public Result(@NonNull InlineActivityResult inlineActivityResult, int requestCode, int resultCode, @Nullable Intent data) {
        this.inlineActivityResult = inlineActivityResult;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public Result(@NonNull InlineActivityResult inlineActivityResult, @Nullable Throwable throwable) {
        this(inlineActivityResult, 0, Activity.RESULT_CANCELED, null);

        this.cause = throwable;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    @Nullable
    public Intent getData() {
        return data;
    }

    @NonNull
    public InlineActivityResult getInlineActivityResult() {
        return inlineActivityResult;
    }

    @Nullable
    public Throwable getCause() {
        return cause;
    }
}
