package com.github.florent37.inlineactivityresult;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener;
import com.github.florent37.inlineactivityresult.callbacks.FailCallback;
import com.github.florent37.inlineactivityresult.callbacks.SuccessCallback;
import com.github.florent37.inlineactivityresult.request.Request;
import com.github.florent37.inlineactivityresult.request.RequestFabric;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all callback listeners and methods for start requests.
 */
public class InlineActivityResult {

    private static final String TAG = "ACTIVITY_RESULT_FRAGMENT_WEEEEE";

    private final Reference<FragmentActivity> activityReference;
    private final Reference<Fragment> fragmentReference;

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

        @Override
        public void error(Throwable throwable) {
            onReceivedActivityException(throwable);
        }
    };
    //endregion

    public static InlineActivityResult startForResult(final FragmentActivity activity, @Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        return new InlineActivityResult(activity).startForResult(intent, listener);
    }

    public static InlineActivityResult startForResult(final Fragment fragment, @Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        return new InlineActivityResult(fragment).startForResult(intent, listener);
    }

    public static InlineActivityResult startForResult(final FragmentActivity activity, @Nullable final Request request, @Nullable final ActivityResultListener listener) {
        return new InlineActivityResult(activity).startForResult(request, listener);
    }

    public static InlineActivityResult startForResult(final Fragment fragment, @Nullable final Request request, @Nullable final ActivityResultListener listener) {
        return new InlineActivityResult(fragment).startForResult(request, listener);
    }

    /**
     * Instantiates with activity.
     *
     * @param activity The activity, source of request.
     */
    public InlineActivityResult(@Nullable final FragmentActivity activity) {
        if (activity != null) {
            this.activityReference = new WeakReference<>(activity);
        } else {
            this.activityReference = new WeakReference<>(null);
        }

        this.fragmentReference = new WeakReference<>(null);
    }

    /**
     * Instantiates with fragment.
     *
     * @param fragment The fragment, source of request.
     */
    public InlineActivityResult(@Nullable final Fragment fragment) {
        FragmentActivity activity = null;

        if (fragment != null) {
            activity = fragment.getActivity();

            this.fragmentReference = new WeakReference<>(fragment);
        } else {
            this.fragmentReference = new WeakReference<>(null);
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

    private void onReceivedActivityException(Throwable throwable) {
        final Result result = new Result(this, throwable);

        for (FailCallback callback : failCallbacks) {
            callback.onFailed(result);
        }
        for (ActivityResultListener listener : responseListeners) {
            listener.onFailed(result);
        }
    }

    public InlineActivityResult startForResult(@Nullable final Intent intent) {
        return startForResult(RequestFabric.create(intent));
    }

    public InlineActivityResult startForResult(@Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        return startForResult(RequestFabric.create(intent), listener);
    }

    public InlineActivityResult startForResult(@Nullable final Request request) {
        if (request != null) {
            this.start(request);
        }
        return this;
    }

    public InlineActivityResult startForResult(@Nullable final Request request, @Nullable final ActivityResultListener listener) {
        if (request != null) {
            if (listener != null) {
                this.responseListeners.add(listener);
            }

            this.start(request);
        }
        return this;
    }

    /**
     * Add callback function on success result.
     *
     * @param callback The callback.
     * @return The inline activity result.
     */
    public InlineActivityResult onSuccess(@Nullable final SuccessCallback callback) {
        if (callback != null) {
            successCallbacks.add(callback);
        }
        return this;
    }

    /**
     * Add callback function on fail result.
     *
     * @param callback The callback.
     * @return The inline activity result.
     */
    public InlineActivityResult onFail(@Nullable final FailCallback callback) {
        if (callback != null) {
            failCallbacks.add(callback);
        }
        return this;
    }

    private void start(@NonNull final Request request) {
        final FragmentActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {

            listener.error(new ActivityNotFoundException("activity is null or finished"));

            return;
        }

        final ActivityResultFragment newFragment = ActivityResultFragment.newInstance(request, listener);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager;

                Fragment fragment = fragmentReference.get();
                if (fragment != null) {
                    fragmentManager = fragment.getChildFragmentManager();
                } else {
                    fragmentManager = activity.getSupportFragmentManager();
                }

                fragmentManager.beginTransaction()
                        .add(newFragment, TAG)
                        .commitNowAllowingStateLoss();

            }
        });
    }
}
