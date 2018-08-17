package jm.study.dagger2demo.http.callback;

/**
 * @author jamin
 *         Created on 2018/5/31.
 *         网络请求回调接口
 */

public interface RequestCallBack<T> {
    /**
     * 请求前的一些初始化工作，比如显示进度条、需要隐藏某个view等
     */
    void onBefore();

    /**
     * 请求完成调用
     */
    void onComplete();

    /**
     * 请求失败回调
     * @param errorMsg 错误信息
     */
    void onFailure(String errorMsg);

    /**
     * 请求成功回调
     * @param data 服务器返回的数据
     */
    void onSuccess(T data);
}
