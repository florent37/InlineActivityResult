package com.github.florent37.inlineactivityresult.request;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * The type Request activity for result. {@link android.app.Activity#startActivityForResult(Intent, int, Bundle)}
 */
public class RequestActivityForResult implements Request {

    private final Intent intent;

    @Nullable
    private Bundle options;

    /**
     * Object creator for {@link android.os.Parcelable}.
     */
    public static final Creator<RequestActivityForResult> CREATOR = new Creator<RequestActivityForResult>() {
        public RequestActivityForResult createFromParcel(Parcel in) {
            return new RequestActivityForResult(in);
        }

        public RequestActivityForResult[] newArray(int size) {
            return new RequestActivityForResult[size];
        }
    };

    /**
     * Instantiates a new Request activity for result.
     *
     * @param intent  The intent to start.
     * @param options Additional options for how the Activity should be started.
     */
    public RequestActivityForResult(Intent intent, @Nullable Bundle options) {
        this.intent = intent;
        this.options = options;
    }

    private RequestActivityForResult(Parcel in) {
        this((Intent) in.readParcelable(Intent.class.getClassLoader()), (Bundle) in.readParcelable(Bundle.class.getClassLoader()));
    }

    @NonNull
    public Intent getIntent() {
        return intent;
    }

    @Override
    public void execute(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(intent, flags);
        dest.writeParcelable(options, flags);
    }

}
