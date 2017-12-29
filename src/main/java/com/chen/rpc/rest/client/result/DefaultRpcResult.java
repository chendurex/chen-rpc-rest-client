package com.chen.rpc.rest.client.result;

import java.io.Serializable;

/**
 * @author chen
 * @description
 * @date 2017/3/6 10:29
 */
public class DefaultRpcResult<T> extends AbstractResult<T> implements Serializable {
    private static final long serialVersionUID = 487811507154123333L;
    private int result;
    private String resultDesc;
    private Throwable throwable;

    public DefaultRpcResult(int result, String resultDesc, Throwable throwable, T value) {
        super(value);
        this.result = result;
        this.resultDesc = resultDesc;
        this.throwable = throwable;
    }

    public DefaultRpcResult(String resultDesc) {
        this(FAIL, resultDesc, null, null);
    }

    public DefaultRpcResult(T value) {
        this(SUCCESS, "success", null, value);
    }

    public DefaultRpcResult() {
        this(SUCCESS, "success", null, null);
    }

    @Override
    public int getResult() {
        return result;
    }

    @Override
    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String getResultDesc() {
        return this.resultDesc;
    }

    @Override
    public Throwable getException() {
        return this.throwable;
    }

    @Override
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    @Override
    public void setException(Throwable throwable) {
        this.throwable = throwable;
    }
}
