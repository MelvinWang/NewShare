package com.melvin.share.rx;


import android.content.Context;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by a on 2016/5/6.
 */
public class RxFragmentHelper<T> {
    //子线程运行，主线程回调
    public Observable.Transformer<T, T> ioMain(final Context context, final RxFragment fragment, final boolean isShowProgress) {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {

                Observable<T> tObservable1 = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                if (isShowProgress) {
                                    ProgressDialogUtil.showProgress(context, "正在加载，请稍候...");
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(fragment.lifecycle(), FragmentEvent.DESTROY));

                return tObservable1;

            }
        };
    }
}
