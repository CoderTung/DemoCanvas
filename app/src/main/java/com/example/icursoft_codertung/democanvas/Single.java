package com.example.icursoft_codertung.democanvas;

/**
 * Created by iCurSoft_CoderTung on 2017/12/1.
 */

public class Single {
/*
    //饿汉式单例
    private static Single s = new Single();

    private Single(){}

    public static Single getInstance(){
        return s;
    }
*/

    //懒汉式单例
    private static Single s = null;

    private Single(){}

    public static Single getInstance(){
        if (s == null){
            synchronized (Single.class){
                if (s == null){
                    s = new Single();
                }
            }
        }
        return s;
    }

}
