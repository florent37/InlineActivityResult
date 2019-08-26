package com.github.florent37.inlineactivityresult;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener;
import com.github.florent37.inlineactivityresult.callbacks.FailCallback;
import com.github.florent37.inlineactivityresult.callbacks.SuccessCallback;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class InlineActivityResult {

    private static final String TAG = "ACTIVITY_RESULT_FRAGMENT_WEEEEE";

    private final Reference<FragmentActivity> activityReference;

    //region callbacks
    private final List<ActivityResultListener> responseListeners = new ArrayList<>();
    private final List<SuccessCallback> successCallbacks = new ArrayList<>();
    private final List<FailCallback> failCallbacks = new ArrayList<>();

    //the listener we will give to the fragment
    private final ActivityResultFragment.ActivityResultListener listener = new ActivityResultFragment.ActivityResultListener() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            onReceivedActivityResult(requestCode, resultCode, data);
        }
    };
    //endregion

    public static InlineActivityResult startForResult(final FragmentActivity activity, @Nullable final Intent intent, @Nullable final ActivityResultListener listener){
        return new InlineActivityResult(activity).startForResult(intent, listener);
    }

    public static InlineActivityResult startForResult(final Fragment fragment, @Nullable final Intent intent, @Nullable final ActivityResultListener listener){
        return new InlineActivityResult(fragment).startForResult(intent, listener);
    }

    public InlineActivityResult(@Nullable final FragmentActivity activity) {
        if (activity != null) {
            this.activityReference = new WeakReference<>(activity);
        } else {
            this.activityReference = new WeakReference<>(null);
        }
    }

    public InlineActivityResult(@Nullable final Fragment fragment) {
        FragmentActivity activity = null;
        if (fragment != null) {
            activity = fragment.getActivity();
        }
        if (activity != null) {
            this.activityReference = new WeakReference<>(activity);
        } else {
            this.activityReference = new WeakReference<>(null);
        }
    }

    private void onReceivedActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        final Result result = new Result(this, requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            for (SuccessCallback callback : successCallbacks) {
                callback.onSuccess(result);
            }
            for (ActivityResultListener listener : responseListeners) {
                listener.onSuccess(result);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            for (FailCallback callback : failCallbacks) {
                callback.onFailed(result);
            }
            for (ActivityResultListener listener : responseListeners) {
                listener.onFailed(result);
            }
        }
    }

    public InlineActivityResult startForResult(@Nullable final Intent intent) {
        if (intent != null) {
            this.start(intent);
        }
        return this;
    }

    public InlineActivityResult startForResult(@Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        if (intent != null && listener != null) {
            this.responseListeners.add(listener);
            this.start(intent);
        }
        return this;
    }

    public InlineActivityResult onSuccess(@Nullable final SuccessCallback callback) {
        if (callback != null) {
            successCallbacks.add(callback);
        }
        return this;
    }

    public InlineActivityResult onFail(@Nullable final FailCallback callback) {
        if (callback != null) {
            failCallbacks.add(callback);
        }
        return this;
    }

    private void start(@NonNull final Intent intent) {
        final FragmentActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        final ActivityResultFragment oldFragment = (ActivityResultFragment) activity
                .getSupportFragmentManager()
                .findFragmentByTag(TAG);

        if (oldFragment != null) {
            oldFragment.setListener(listener);
        } else {
            final ActivityResultFragment newFragment = ActivityResultFragment.newInstance(intent);
            newFragment.setListener(listener);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(newFragment, TAG)
                            .commitNowAllowingStateLoss();
                }
            });

        }
    }
}
