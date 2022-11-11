package com.serverktpm.service;


import com.serverktpm.model.Member;
import com.serverktpm.model.User;
import com.serverktpm.repository.ImageRepository;
import com.serverktpm.repository.UserRepository;
import com.serverktpm.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final UserRepository userRepo;
    private final ImageRepository imageRepo;

    public MemberService(UserRepository userRepo, ImageRepository imageRepo) {
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
    }

    public List<Member> getListMemberOfConversation(List<String> listMemberId){
        List<Member> memberList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(listMemberId)){
            listMemberId.stream().forEach(id -> {
                User user = userRepo.findById(id).get();
                if (user != null){
                    Member member = MapperUtil.mapObject(user,Member.class);
                    member.setAvatar(StringUtils.hasText(user.getAvatar()) ? imageRepo.findById(user.getAvatar()).get().getBase64(): "");
                    memberList.add(member);
                }
            });
        }
        return memberList;
    }

}
