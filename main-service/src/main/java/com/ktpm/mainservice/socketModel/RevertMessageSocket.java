package com.ktpm.mainservice.socketModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevertMessageSocket {
    private String messageId;
    private String accessToken;
}
