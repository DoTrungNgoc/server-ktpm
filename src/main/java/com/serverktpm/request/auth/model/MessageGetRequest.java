package com.serverktpm.request.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageGetRequest {
    private String conversationId;
    private Integer pageNumber = 0;
    private Integer pageSize = 50;
}
