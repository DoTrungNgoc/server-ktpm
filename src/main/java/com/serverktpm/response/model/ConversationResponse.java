package com.serverktpm.response.model;

import com.serverktpm.model.Member;
import com.serverktpm.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse {
    private String id;
    private String name;
    private Integer type;
    private String avatar;
    private String coverImage;
    private List<Member> listMember;
    private Date createdDate;
    private Message lastMessage;
}
