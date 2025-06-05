package edu.fdu.ivos.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StatusCode {
    SUCCESS(2000, "OK"),
    LOGIN_SUCCESS(2001, "Login successful"),
    OPERATION_SUCCESS(2002, "Operation successful"),

    NOT_LOGIN(4000, "Not logged in"),
    PASSWORD_ERROR(4002, "Incorrect username or password"),
    USERNAME_ERROR(4003, "Incorrect username or password"),

    OPERATION_FAILED(5001, "Operation failed"),
    VALIDATE_ERROR(5002, "Invalid request parameters"),
    USERNAME_ALREADY_EXISTS(5003, "Username already exists"),
    DATA_UNEXISTS(5004, "Requested data does not exist");

    // Status code and corresponding description
    private Integer code;
    private String msg;
}
