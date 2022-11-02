package com.serverktpm.model;

import com.serverktpm.common.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "friendRequests")
public class FriendRequest {
    @Id
    private String id;
    private String fromUserId;
    private String toUserId;
    private Integer status = FriendRequestStatus.WAITING;
    private Date timeSend = new Date();
}
