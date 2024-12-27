//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lty.bookstore.system.backend.base;

public interface BaseStatusCode {
    StatusCode SUCCESS = new StatusCode("1", "Success", "Success");
    StatusCode ERROR_COMMON = new StatusCode("2", "Business error", "Business error");
    StatusCode ERROR_PARAM_VALID_FAIL = new StatusCode("3", "Param check failed", "Param check failed");
    StatusCode ERROR_AUTH_FAIL = new StatusCode("4", "user auth check failed", "Please re-login");
}
