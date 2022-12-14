package com.serverktpm.socketModel;

import com.serverktpm.response.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestSocket {
    private String id;
    private UserResponse fromUser;
    private Date timeSend;
}
