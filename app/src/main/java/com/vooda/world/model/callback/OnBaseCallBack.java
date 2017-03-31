package com.vooda.world.model.callback;

/**
 * Created by leijiaxq
 * Data       2016/12/27 11:38
 * Describe
 */
public interface OnBaseCallBack<T> {

    void onSuccess(T t);
    void onFailed(String msg);

}
