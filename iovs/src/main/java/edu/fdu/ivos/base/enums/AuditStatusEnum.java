package edu.fdu.ivos.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* Vehicle audit status enumeration */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    MY_PENDING("10", "Pending my review"),
    PENDING("20", "Pending review by others"),
    AUDITED("30", "Reviewed"),
    REJECT("40", "Rejected");

    private String code;
    private String msg;
}
