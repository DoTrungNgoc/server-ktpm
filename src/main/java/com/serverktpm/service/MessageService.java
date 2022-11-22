package com.serverktpm.service;

import com.serverktpm.exception.ServiceException;
import com.serverktpm.model.Message;
import com.serverktpm.model.MessageFile;
import com.serverktpm.repository.MessageFileRepository;
import com.serverktpm.repository.MessageRepository;
import com.serverktpm.request.auth.model.MessageGetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessageService {
    private final MessageRepository messageRepo;
    private final S3Service s3Service;
    private final MessageFileRepository fileRepository;

    public MessageService(MessageRepository messageRepo, S3Service s3Service, MessageFileRepository fileRepository) {
        this.messageRepo = messageRepo;
        this.s3Service = s3Service;
        this.fileRepository = fileRepository;
    }

    public Page<Message> getMessageOfConversation(MessageGetRequest request){
        Pageable page = PageRequest.of(request.getPageNumber(),request.getPageSize());
        return messageRepo.findMessagesByConversationIdOrderByTimeSendDesc(request.getConversationId(),page);
    }

    public MessageFile uploadMessageFile(String id, MultipartFile file){
        String url =  s3Service.uploadFile(file);
        if (!StringUtils.hasText(url)) {
            throw new ServiceException("Can not upload message file");
        }
        fileRepository.findById(id).ifPresent(messageFile -> {
            throw new ServiceException("Id message file had existed");
        });
        MessageFile messageFile = MessageFile.builder()
                .id(id)
                .fileName(file.getOriginalFilename())
                .url(url).build();
        fileRepository.save(messageFile);
        return messageFile;
    }
}
