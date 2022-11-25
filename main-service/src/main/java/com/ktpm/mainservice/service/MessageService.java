package com.ktpm.mainservice.service;


import com.ktpm.mainservice.exception.ServiceException;
import com.ktpm.mainservice.model.Message;
import com.ktpm.mainservice.model.MessageFile;
import com.ktpm.mainservice.repository.MessageFileRepository;
import com.ktpm.mainservice.repository.MessageRepository;
import com.ktpm.mainservice.request.auth.model.MessageGetRequest;
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
        String url;
        try {
            url =  s3Service.uploadFile(file);
        } catch (Exception e){
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
