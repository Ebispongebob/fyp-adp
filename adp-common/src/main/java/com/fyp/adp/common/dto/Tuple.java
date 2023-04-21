package com.fyp.adp.common.dto;

import java.io.Serializable;

public class Tuple<T, R> implements Serializable {

    private T t;
    private R r;

    public Tuple(T t, R r) {
        this.t = t;
        this.r = r;
    }

    public T getFirst() {
        return t;
    }

    public R getSecond() {
        return r;
    }

    @Override
    public int hashCode() {
        return (null == t ? 0 : t.hashCode()) + (null == r ? 0 : r.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Tuple) {
            Tuple target = (Tuple) obj;
            if (t == null ? target.getFirst() != null : !t.equals(target.getFirst())) {
                return false;
            }
            if (r == null ? target.getSecond() != null : !r.equals(target.getSecond())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "t=" + t +
                ", r=" + r +
                '}';
    }
}
