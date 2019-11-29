package com.github.florent37.inlineactivityresult.request;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Arguments storage for method {@link androidx.fragment.app.Fragment#startIntentSenderForResult(IntentSender, int, Intent, int, int, int, Bundle)}.
 */
public class RequestIntentSender implements Request {

    private IntentSender intentSender;

    @Nullable
    private Intent fillInIntent;

    private int flagsMask, flagsValues, extraFlags;

    @Nullable
    private Bundle options;

    /**
     * Object creator for {@link android.os.Parcelable}.
     */
    public static final Creator<RequestIntentSender> CREATOR = new Creator<RequestIntentSender>() {
        public RequestIntentSender createFromParcel(Parcel in) {
            return new RequestIntentSender(in);
        }

        public RequestIntentSender[] newArray(int size) {
            return new RequestIntentSender[size];
        }
    };

    /**
     * For more information see {@link android.app.Activity#startIntentSenderForResult(IntentSender, int, Intent, int, int, int, Bundle)}.
     */
    public RequestIntentSender(IntentSender intentSender,
                               @Nullable Intent fillInIntent,
                               int flagsMask,
                               int flagsValues,
                               int extraFlags,
                               @Nullable Bundle options) {
        this.intentSender = intentSender;
        this.fillInIntent = fillInIntent;
        this.flagsMask = flagsMask;
        this.flagsValues = flagsValues;
        this.extraFlags = extraFlags;
        this.options = options;
    }

    private RequestIntentSender(Parcel in) {
        intentSender = in.readParcelable(IntentSender.class.getClassLoader());
        fillInIntent = in.readParcelable(Intent.class.getClassLoader());
        flagsMask = in.readInt();
        extraFlags = in.readInt();
        options = in.readParcelable(Bundle.class.getClassLoader());
    }

    public IntentSender getIntentSender() {
        return intentSender;
    }

    @Nullable
    public Intent getFillInIntent() {
        return fillInIntent;
    }

    public int getFlagsMask() {
        return flagsMask;
    }

    public int getFlagsValues() {
        return flagsValues;
    }

    public int getExtraFlags() {
        return extraFlags;
    }

    @Nullable
    public Bundle getOptions() {
        return options;
    }

    @Override
    public void execute(@NonNull Fragment fragment, int requestCode) throws Exception {
        startIntentSender(fragment, this, requestCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(intentSender, flags);
        dest.writeParcelable(fillInIntent, flags);
        dest.writeInt(flagsMask);
        dest.writeInt(extraFlags);
        dest.writeParcelable(options, flags);
    }

    private void startIntentSender(@NonNull Fragment fragment, @NonNull RequestIntentSender request, int requestCode) throws IntentSender.SendIntentException {
        fragment.startIntentSenderForResult(
                request.getIntentSender(),
                requestCode,
                request.getFillInIntent(),
                request.getFlagsMask(),
                request.getFlagsValues(),
                request.getExtraFlags(),
                request.getOptions()
        );
    }

}
