package com.serverktpm.socketModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSocket {
    private String conversationId;
    private List<String> content;
    private Integer type;
    private String  accessToken;
    private String replyMessageId;
}
