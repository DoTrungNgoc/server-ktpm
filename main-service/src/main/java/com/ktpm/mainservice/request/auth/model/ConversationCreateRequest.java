package com.ktpm.mainservice.request.auth.model;

import lombok.Data;

import java.util.List;

@Data
public class ConversationCreateRequest {
    private String name;
    private String avatar;
    private List<String> listMemberId;
}
