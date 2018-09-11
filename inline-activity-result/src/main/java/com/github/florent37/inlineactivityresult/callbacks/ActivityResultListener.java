package com.github.florent37.inlineactivityresult.callbacks;

import com.github.florent37.inlineactivityresult.Result;

public interface ActivityResultListener {
    void onSuccess(Result result);
    void onFailed(Result result);
}
