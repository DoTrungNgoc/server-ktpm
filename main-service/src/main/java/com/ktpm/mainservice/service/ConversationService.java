package com.ktpm.mainservice.service;




import com.ktpm.mainservice.common.ConversationType;
import com.ktpm.mainservice.exception.NotFoundException;
import com.ktpm.mainservice.exception.ServiceException;
import com.ktpm.mainservice.model.Conversation;
import com.ktpm.mainservice.model.User;
import com.ktpm.mainservice.repository.ConversationRepository;
import com.ktpm.mainservice.repository.UserRepository;
import com.ktpm.mainservice.request.auth.model.ConversationCreateRequest;
import com.ktpm.mainservice.response.model.ConversationResponse;
import com.ktpm.mainservice.util.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConversationService {
    private final ConversationRepository conversationRepo;
    private final UserRepository userRepo;
    private final AuthService authService;;
    private final MemberService memberService;

    public ConversationService(ConversationRepository conversationRepo, UserRepository userRepo, AuthService authService, MemberService memberService) {
        this.conversationRepo = conversationRepo;
        this.userRepo = userRepo;
        this.authService = authService;
        this.memberService = memberService;
    }

    public Conversation createConversation(ConversationCreateRequest request) {
        String userId = authService.getLoggedUserId();
        User user  = userRepo.findById(userId).get();
        List<String> listMemberId = request.getListMemberId();
        listMemberId =  listMemberId.stream().distinct().filter(s -> !s.equals(userId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(listMemberId)){
            throw new ServiceException("List member can not empty");
        }
        listMemberId.forEach(id -> {
            User userMember = userRepo.findByIdAndIsBlock(id, false);
            if (userMember == null){
                throw new NotFoundException("Not found member id: " + id);
            }
        });
        listMemberId.add(userId);
        if (conversationRepo.existsByListMemberId(listMemberId)){
            throw new ServiceException("Conversation had existed");
        }
        Conversation conversation = new Conversation();
        conversation.setAvatar(request.getAvatar());
        conversation.setName(request.getName());
        conversation.setAvatar(request.getAvatar());
        conversation.setType(listMemberId.size()>2 ? ConversationType.GROUP : ConversationType.COUPLE);
        conversation.setListMemberId(listMemberId);
        return conversationRepo.save(conversation);
    }

    public List<ConversationResponse> getAllConversationOfUser(){
        List<Conversation> conversationList = conversationRepo.findConversationsOfUser(authService.getLoggedUserId());
        List<ConversationResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(conversationList)){
            conversationList.stream().forEach(conversation -> {
                ConversationResponse conversationResponse = MapperUtil.mapObject(conversation, ConversationResponse.class);
                conversationResponse.setListMember(memberService.getListMemberOfConversation(conversation.getListMemberId()));
                responses.add(conversationResponse);
            });
        }
        return responses;
    }


}
