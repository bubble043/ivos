package edu.fdu.ivos.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* Vehicle application status enumeration */
@Getter
@AllArgsConstructor
public enum ApplicationStatusEnum {
    PENDING("10", "Initiated"),
    CANCEL("20", "Cancelled"),
    AUDIT("30", "Under review"),
    REJECT("40", "Rejected"),
    AUDITED("50", "Reviewed"),
    ALLOCATION("60", "Vehicle allocated"),
    END("70", "Application completed");

    private String code;
    private String msg;
}
