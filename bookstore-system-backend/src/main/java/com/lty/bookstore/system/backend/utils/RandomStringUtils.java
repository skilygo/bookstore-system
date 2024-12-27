//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lty.bookstore.system.backend.utils;

public class RandomStringUtils {

    public static String getRandomNumberStr(int len) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; ++i) {
            code = code.append((int) Math.floor(Math.random() * 10.0));
        }
        return code.toString();
    }
}
