package com.github.florent37.inlineactivityresult.request;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Interface for store data to call activity request.
 */
public interface Request extends Parcelable {

    /**
     * Execute request in selected {@code fragment} with {@code requestCode}.
     *
     * @param fragment    The fragment.
     * @param requestCode The request code.
     * @throws Exception The exception if throws exception.
     */
    void execute(@NonNull Fragment fragment, int requestCode) throws Exception;

}
