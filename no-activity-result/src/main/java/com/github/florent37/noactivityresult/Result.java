package com.github.florent37.noactivityresult;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Result {
    @NonNull
    private final NoActivityResult noActivityResult;

    private final int requestCode;
    private final int resultCode;

    @Nullable
    private final Intent data;

    public Result(@NonNull NoActivityResult noActivityResult, int requestCode, int resultCode, @Nullable Intent data) {
        this.noActivityResult = noActivityResult;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
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
    public NoActivityResult getNoActivityResult() {
        return noActivityResult;
    }
}
