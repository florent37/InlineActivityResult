package com.github.florent37.noactivityresult.callbacks;

import com.github.florent37.noactivityresult.Result;

public interface ActivityResultListener {
    void onSuccess(Result result);
    void onFailed(Result result);
}
