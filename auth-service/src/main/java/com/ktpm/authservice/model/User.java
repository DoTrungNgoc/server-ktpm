package com.ktpm.authservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String phoneNumber;
    private String avatar;
    private String coverImage;
    private Date dateOfBirth = new Date(2000,01,01);
    private String password;
    private List<String> phoneBooks = new ArrayList<>();
    private boolean isAdmin = false;
    private boolean isBlock = false;
    private boolean isActive = true;
    private Date createdDate = new Date();
}
