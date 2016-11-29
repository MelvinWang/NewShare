package com.melvin.share.view;



import com.melvin.share.model.serverReturn.BaseReturnModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jam on 16-6-12
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {
    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T>  Observable.Transformer<BaseReturnModel<T>, T> handleResult() {

        return new Observable.Transformer<BaseReturnModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseReturnModel<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseReturnModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseReturnModel<T> result) {
                        if (result.success) {
                            return createData(result.result);
                        }    else {
                            return Observable.error(new ServerException(""));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }


}
