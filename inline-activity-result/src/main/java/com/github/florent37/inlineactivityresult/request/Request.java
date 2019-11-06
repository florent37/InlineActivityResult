package com.github.florent37.inlineactivityresult.request;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface Request extends Parcelable {

    void execute(@NonNull Fragment fragment, int requestCode) throws Exception;

}
