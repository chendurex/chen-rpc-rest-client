package com.chen.rpc.rest.client.result;

/**
 * @author chen
 *         2017/11/10 20:52
 */
public final class RpcResultUtil {
    public static boolean isSuc(int code) {
        return RpcResult.SUCCESS == code;
    }

    public static boolean isFail(int code) {
        return RpcResult.FAIL == code;
    }

    public static boolean isException(int code) {
        return RpcResult.EXCEPTION == code;
    }

    public static <T>DefaultRpcResult<T> failResult(Throwable t) {
        return new DefaultRpcResult<>(RpcResult.EXCEPTION, t.getMessage(), t, null);
    }

    public static <T>DefaultRpcResult<T> valueResult(T value) {
        return new DefaultRpcResult<>(value);
    }

    public static <T>DefaultRpcResult<T> sucResult() {
        return new DefaultRpcResult<>();
    }

    public static <T>DefaultRpcResult<T> failResult(String resultDesc) {
        return new DefaultRpcResult<>(resultDesc);
    }
}
