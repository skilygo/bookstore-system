//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lty.bookstore.system.backend.base;

import java.util.Objects;

public class StatusCode {
    private String code;
    private String errorTip;
    private String userTip;

    public StatusCode(String code, String errorTip, String userTip) {
        this.code = code;
        this.errorTip = errorTip;
        this.userTip = userTip;
    }

    public String getCode() {
        return this.code;
    }

    public String getUserTip() {
        return this.userTip;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            StatusCode stausCode = (StatusCode)o;
            return this.code.equals(stausCode.code);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.code});
    }
}
