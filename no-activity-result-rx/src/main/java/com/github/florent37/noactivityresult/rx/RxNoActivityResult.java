package com.github.florent37.noactivityresult.rx;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.florent37.noactivityresult.NoActivityResult;
import com.github.florent37.noactivityresult.Result;
import com.github.florent37.noactivityresult.callbacks.ActivityResultListener;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class RxNoActivityResult {

    private final NoActivityResult noActivityResult;

    public RxNoActivityResult(final FragmentActivity activity) {
        noActivityResult = new NoActivityResult(activity);
    }

    public RxNoActivityResult(final Fragment fragment) {
        noActivityResult = new NoActivityResult(fragment.getActivity());
    }

    public Observable<Result> request(final Intent intent) {
        return Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(final ObservableEmitter<Result> emitter) throws Exception {
                noActivityResult
                        .startForResult(intent, new ActivityResultListener() {
                            @Override
                            public void onSuccess(Result result) {
                                emitter.onNext(result);
                                emitter.onComplete();
                            }

                            @Override
                            public void onFailed(Result result) {
                                emitter.onError(new Error(result));
                            }
                        });
            }
        });
    }

    public Single<Result> requestAsSingle(final Intent intent) {
        return Single.create(new SingleOnSubscribe<Result>() {
            @Override
            public void subscribe(final SingleEmitter<Result> emitter) throws Exception {
                noActivityResult
                        .startForResult(intent, new ActivityResultListener() {
                            @Override
                            public void onSuccess(Result result) {
                                emitter.onSuccess(result);
                            }

                            @Override
                            public void onFailed(Result result) {
                                emitter.onError(new Error(result));
                            }
                        });
            }
        });
    }

    public Flowable<Result> requestAsFlowable(final Intent intent) {
        return Flowable.create(new FlowableOnSubscribe<Result>() {
            @Override
            public void subscribe(final FlowableEmitter<Result> emitter) throws Exception {
                noActivityResult
                        .startForResult(intent, new ActivityResultListener() {
                            @Override
                            public void onSuccess(Result result) {
                                emitter.onNext(result);
                            }

                            @Override
                            public void onFailed(Result result) {
                                emitter.onError(new Error(result));
                            }
                        });
            }
        }, BackpressureStrategy.LATEST);
    }

    public Maybe<Result> requestAsMaybe(final Intent intent) {
        return Maybe.create(new MaybeOnSubscribe<Result>() {
            @Override
            public void subscribe(final MaybeEmitter<Result> emitter) throws Exception {
                noActivityResult.startForResult(intent, new ActivityResultListener() {
                    @Override
                    public void onSuccess(Result result) {
                        emitter.onSuccess(result);
                    }

                    @Override
                    public void onFailed(Result result) {
                        emitter.onError(new Error(result));
                    }
                });
            }
        });
    }

    public static class Error extends Throwable {
        private final Result result;

        private Error(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return result;
        }
    }
}
