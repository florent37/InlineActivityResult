package com.github.florent37.inlineactivityresult;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Stores data from {@link androidx.fragment.app.Fragment#onActivityResult(int, int, Intent)} method
 * or {@link Throwable} value, if request executed with exception throws.
 */
public class Result {
    @NonNull
    private final InlineActivityResult inlineActivityResult;

    private final int requestCode;
    private final int resultCode;

    @Nullable
    private final Intent data;

    @Nullable
    private Throwable cause;

    /**
     * Request executed as normal, (@code requestCode) point to success of operation.
     *
     * @param inlineActivityResult Reference to {@link InlineActivityResult}.
     * @param requestCode          Request code, by default eq {@link ActivityResultFragment#REQUEST_CODE}.
     * @param resultCode           Result code, {@link Activity#RESULT_CANCELED} or {@link Activity#RESULT_OK}.
     * @param data                 Result data.
     */
    public Result(@NonNull InlineActivityResult inlineActivityResult, int requestCode, int resultCode, @Nullable Intent data) {
        this.inlineActivityResult = inlineActivityResult;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    /**
     * Request executed with exception.
     *
     * @param inlineActivityResult Reference to {@link InlineActivityResult}.
     * @param throwable            Throwable value.
     * @see #getCause()
     */
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

    /**
     * Gets cause of exception.
     * Return NonNull value if request executed with exception
     *
     * @return the cause
     */
    @Nullable
    public Throwable getCause() {
        return cause;
    }
}
