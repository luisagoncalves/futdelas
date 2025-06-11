package org.ifb.tcc.futdelas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String deviceToken;
    private String title;
    private String body;
}
