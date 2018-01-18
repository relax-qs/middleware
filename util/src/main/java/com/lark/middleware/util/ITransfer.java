package com.lark.middleware.util;

/**
 * Created by houenxun on 16/4/25.
 */
public interface ITransfer<S, T> {
    T transfer(S s);
}
