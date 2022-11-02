package com.serverktpm.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "conversations")
public class Conversation {
    @Id
    private String id;
    private String name;
    private Integer type;
    private String avatar;
    private String coverImage;
    private List<String> listMemberId;
    private Date createdDate = new Date();
    private Message lastMessage;
}
