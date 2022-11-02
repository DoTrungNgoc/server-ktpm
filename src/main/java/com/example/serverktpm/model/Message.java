package com.example.serverktpm.model;


import com.example.serverktpm.common.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String conversationId;
    private List<String> content = new ArrayList<>();
    private Integer type = MessageType.TEXT;
    private String  senderId;
    private Date timeSend = new Date();
    private String replyMessageId;
    private List<Integer> reactList;
}
