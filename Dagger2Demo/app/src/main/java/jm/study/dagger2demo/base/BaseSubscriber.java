package jm.study.dagger2demo.base;


import com.orhanobut.logger.Logger;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import jm.study.dagger2demo.http.callback.RequestCallBack;
import jm.study.dagger2demo.utils.NetworkUtils;
import retrofit2.HttpException;
import rx.Subscriber;

/**
 * @author jamin
 *         Created on 2018/7/9.
 *         <p>
 *         统一处理回调各个方法，并且这里对返回错误做了统一处理
 */

public class BaseSubscriber<T> extends Subscriber<T> {

    private RequestCallBack<T> mRequestCallback;

    public BaseSubscriber(RequestCallBack<T> requestCallBack) {
        mRequestCallback = requestCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mRequestCallback != null) {
            mRequestCallback.onBefore();
        }
    }

    @Override
    public void onCompleted() {
        if (mRequestCallback != null) {
            mRequestCallback.onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e(e.getMessage());
        if (mRequestCallback != null) {
            String errorMsg;
            if (e instanceof HttpException) {
                switch (((HttpException) e).code()) {
                    case 403:
                        errorMsg = "没有权限访问!";
                        break;
                    case 504:
                        if (!NetworkUtils.isConnected()) {
                            errorMsg = "没有联网哦!";
                        } else {
                            errorMsg = "网络连接超时!";
                        }
                        break;
                    default:
                        errorMsg = "请求失败！";
                        break;
                }
            } else if (e instanceof UnknownHostException) {
                errorMsg = "未知名主机！";
            } else if (e instanceof SocketTimeoutException) {
                errorMsg = "网络连接超时！";
            } else {
                errorMsg = "请求失败！";
            }

            mRequestCallback.onFailure(errorMsg);
        }
    }

    @Override
    public void onNext(T t) {
        if (mRequestCallback != null) {
            mRequestCallback.onSuccess(t);
        }
    }
}
