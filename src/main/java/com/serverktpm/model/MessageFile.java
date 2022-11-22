package com.serverktpm.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messageFiles")
@Data
@Builder
public class MessageFile {
    @Indexed(unique = true)
    private String id;
    private String fileName;
    private String url;
}
