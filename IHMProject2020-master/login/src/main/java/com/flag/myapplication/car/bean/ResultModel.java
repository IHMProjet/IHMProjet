package com.flag.myapplication.car.bean;



import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResultModel<T> implements Serializable{
    private int code;
    private String msg;
    private T data ;
    private ArrayList<T> arrayList;

    public ResultModel() {

    }

    public ResultModel(int errorCode, String msg, T data, ArrayList<T> arrayList) {
        this.code = errorCode;
        this.msg = msg;
        this.data = data;
        this.arrayList = arrayList;
    }

    public ArrayList<T> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<T> arrayList) {
        this.arrayList = arrayList;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public static ResultModel fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultModel.class, clazz);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }



}
