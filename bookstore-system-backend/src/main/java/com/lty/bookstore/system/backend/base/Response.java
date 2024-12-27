//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lty.bookstore.system.backend.base;

import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 510019869310111711L;
    private String code;
    private String msg;
    private String errorDetail;
    private T data;
    private String tid;

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.tid = MDC.get("traceId");
    }

    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.tid = MDC.get("traceId");
    }

    public static Response success() {
        return new Response(BaseStatusCode.SUCCESS.getCode(), BaseStatusCode.SUCCESS.getUserTip(), (Object) null);
    }

    public static <T> Response<T> success(T data) {
        return new Response(BaseStatusCode.SUCCESS.getCode(), BaseStatusCode.SUCCESS.getUserTip(), data);
    }

    public static <T> Response<T> successWithId(Number data) {
        HashMap<String, String> map = new HashMap();
        map.put("id", data.toString());
        return new Response(BaseStatusCode.SUCCESS.getCode(), BaseStatusCode.SUCCESS.getUserTip(), map);
    }

    public static <T> Response<T> successWithList(Collection<?> data) {
        HashMap<String, Collection<?>> map = new HashMap();
        map.put("list", data);
        return new Response(BaseStatusCode.SUCCESS.getCode(), BaseStatusCode.SUCCESS.getUserTip(), map);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response(BaseStatusCode.SUCCESS.getCode(), message, data);
    }

    public static Response fail() {
        return new Response(BaseStatusCode.ERROR_COMMON.getCode(), BaseStatusCode.ERROR_COMMON.getUserTip(), (Object) null);
    }

    public static <T> Response<T> fail(T data) {
        return new Response(BaseStatusCode.ERROR_COMMON.getCode(), BaseStatusCode.ERROR_COMMON.getUserTip(), data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response(BaseStatusCode.ERROR_COMMON.getCode(), message);
    }

    public static <T> Response<T> fail(StatusCode code) {
        return new Response(code.getCode(), code.getUserTip());
    }

    public static <T> Response<T> fail(String code, String message, T data) {
        return new Response(code, message, data);
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public T getData() {
        return this.data;
    }

    public String getTid() {
        return this.tid;
    }

    public Response<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Response<T> setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
        return this;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Response<T> setTid(String tid) {
        this.tid = tid;
        return this;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71:
                {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label71;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label71;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                label57:
                {
                    Object this$errorDetail = this.getErrorDetail();
                    Object other$errorDetail = other.getErrorDetail();
                    if (this$errorDetail == null) {
                        if (other$errorDetail == null) {
                            break label57;
                        }
                    } else if (this$errorDetail.equals(other$errorDetail)) {
                        break label57;
                    }

                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$tid = this.getTid();
                Object other$tid = other.getTid();
                if (this$tid == null) {
                    if (other$tid == null) {
                        return true;
                    }
                } else if (this$tid.equals(other$tid)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $errorDetail = this.getErrorDetail();
        result = result * 59 + ($errorDetail == null ? 43 : $errorDetail.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $tid = this.getTid();
        result = result * 59 + ($tid == null ? 43 : $tid.hashCode());
        return result;
    }

    public String toString() {
        return "Response(code=" + this.getCode() + ", msg=" + this.getMsg() + ", errorDetail=" + this.getErrorDetail() + ", data=" + this.getData() + ", tid=" + this.getTid() + ")";
    }
}
