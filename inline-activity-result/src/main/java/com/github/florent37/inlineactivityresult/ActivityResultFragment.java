package com.github.florent37.inlineactivityresult;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.florent37.inlineactivityresult.request.Request;
import com.github.florent37.inlineactivityresult.request.RequestFabric;

/**
 * DO NOT USE THIS FRAGMENT DIRECTLY!
 * It's only here because fragments have to be public
 */
public class ActivityResultFragment extends Fragment {

    public static final String INTENT_TO_START = "INTENT_TO_START";

    private static final int REQUEST_CODE = 24;

    @Nullable
    private Request request;

    @Nullable
    private ActivityResultListener listener;

    public ActivityResultFragment() {
        setRetainInstance(true);
    }

    public static ActivityResultFragment newInstance(@NonNull final Intent intent) {
        return newInstance(RequestFabric.create(intent));
    }

    /**
     * New instance activity result fragment.
     *
     * @param request the request
     * @return the activity result fragment
     */
    public static ActivityResultFragment newInstance(@NonNull final Request request) {
        final Bundle args = new Bundle();
        args.putParcelable(INTENT_TO_START, request);
        final ActivityResultFragment fragment = new ActivityResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            this.request = arguments.getParcelable(INTENT_TO_START);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (request != null) {
            try {
                request.execute(this, REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();

                if (listener != null) {
                    listener.error(e);
                }
            }
        } else {
            // this shouldn't happen, but just to be sure
            removeFragment();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (listener != null) {
                listener.onActivityResult(requestCode, resultCode, data);
            }
            removeFragment();
        }
    }

    public ActivityResultFragment setListener(@Nullable ActivityResultListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
        return this;
    }

    private void removeFragment() {
        getFragmentManager().beginTransaction()
                .remove(this)
                .commitAllowingStateLoss();
    }

    interface ActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);

        void error(Throwable throwable);
    }
}
