package jm.study.dagger2demo.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author jamin
 *         Created on 2018/7/9.
 *
 *  定义了一个默认的线程模型，大多数情况下，
 *  我们都会在 io 线程发起 request，在主线程处理 response
 */

public class BaseSchedulerTransformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
