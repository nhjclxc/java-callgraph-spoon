package org.nhjclxc.cg;


import java.util.LinkedList;
import java.util.List;

public abstract class CGTestFile {
    public static void main(String[] args) {

    }

    private static void getString1(Integer i, ReturnTestClass returnTestClass) {
        System.out.println("then");
        get(null, null, null);
    }

    public static ReturnTestClass get(String s, Integer i, ReturnTestClass returnTestClass) {
        System.out.println("try");
        List<String> list = new LinkedList<>();
        return returnTestClass;
    }
//
//    public static final boolean isBool(String s, Integer i) {
//        return true;
//    }
//
//    public static synchronized boolean isBool22(String s, Integer i) {
//
//        throw new RuntimeException("RuntimeException");
////        return true;
//    }
//
    public abstract boolean isBool22ss(String s, Integer i);
}

class ReturnTestClass {

}
