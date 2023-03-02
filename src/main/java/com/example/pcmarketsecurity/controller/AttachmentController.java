package com.example.pcmarketsecurity.controller;

import com.example.pcmarketsecurity.entity.Attachment;
import com.example.pcmarketsecurity.entity.AttachmentContent;
import com.example.pcmarketsecurity.repository.AttachmentContentRepository;
import com.example.pcmarketsecurity.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @PostMapping
    public HttpEntity<?> uploadFile(MultipartHttpServletRequest request) {

        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        return ResponseEntity.ok(savedAttachment);
    }


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR', 'OPERATOR')")
    @GetMapping
    public void download(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + attachment.getName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
            }
        }
    }
}
