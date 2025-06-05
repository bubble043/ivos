package edu.fdu.ivos.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
    /*
        1. Internal status code [mandatory]
        2. Status message [mandatory]
        3. Actual data [optional]
     */
    private Integer code;
    private String msg;
    private Object data;

    /**
     * Constructor 1: No-args constructor (Handled by @NoArgsConstructor)
     */

    /**
     * Constructor 2: All-args constructor (Handled by @AllArgsConstructor)
     * Usage: When a response contains specific data
     */

    /**
     * Constructor 3: Status code and message only
     * Usage: When no specific data is needed in the response
     */
    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Constructor 4: Uses StatusCode enum
     * Usage: For responses without specific data
     */
    public JsonResult(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

    /**
     * Constructor 5: Uses StatusCode enum and attaches specific data
     * Usage: For responses that return data
     */
    public JsonResult(StatusCode statusCode, Object data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * Constructor 6: Used to quickly build JsonResult in static methods
     * Usage: For success responses that return data
     */
    public JsonResult(Object data) {
        this.code = StatusCode.SUCCESS.getCode();
        this.msg = StatusCode.SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * Two static helper methods to quickly generate JsonResult objects
     */
    public static JsonResult ok(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult ok() {
        return ok(null);
    }
}
