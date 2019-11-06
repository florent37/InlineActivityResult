package com.github.florent37.inlineactivityresult.request;

import android.content.Intent;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RequestActivityForResult implements Request {

    private final Intent intent;

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

    public RequestActivityForResult(Intent intent) {
        this.intent = intent;
    }

    private RequestActivityForResult(Parcel in) {
        this((Intent) in.readParcelable(Intent.class.getClassLoader()));
    }

    @NonNull
    public Intent getIntent() {
        return intent;
    }

    @Override
    public void execute(@NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(intent, flags);
    }

}
