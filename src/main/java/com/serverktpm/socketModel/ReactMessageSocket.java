package com.serverktpm.socketModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactMessageSocket {
    private String messageId;
    private int typeReact;
    private String accessToken;
}
