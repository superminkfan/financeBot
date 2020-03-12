package com.telegramBot.myUtil;


import java.io.Serializable;


public class MyPair<K,V> implements Serializable{


    private K key;


    public K getKey() { return key; }


    private V value;


    public V getValue() { return value; }


    public MyPair(K key, V value) {
        this.key = key;
        this.value = value;
    }


    @Override
    public String toString() {
        return key + "=" + value;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (key != null ? key.hashCode() : 0);
        hash = 31 * hash + (value != null ? value.hashCode() : 0);
        return hash;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof MyPair) {
            MyPair pair = (MyPair) o;
            if (key != null ? !key.equals(pair.key) : pair.key != null) return false;
            if (value != null ? !value.equals(pair.value) : pair.value != null) return false;
            return true;
        }
        return false;
    }
}
