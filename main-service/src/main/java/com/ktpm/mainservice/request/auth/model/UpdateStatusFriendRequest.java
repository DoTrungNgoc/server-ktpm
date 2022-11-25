package com.ktpm.mainservice.request.auth.model;

import lombok.Data;

@Data
public class UpdateStatusFriendRequest {
    private String id;
    private Integer status;
}
