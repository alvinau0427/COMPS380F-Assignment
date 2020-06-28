package ouhk.comps380f.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.CommentAttachmentRepository;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.CommentAttachment;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentRepository attachmentRepo;
    
    @Resource
    private CommentAttachmentRepository commentAttachmentRepo;

    @Override
    @Transactional
    public Attachment getAttachment(long topicId, String name) {
        return attachmentRepo.findByTopicIdAndName(topicId, name);
    }
    
    @Override
    @Transactional
    public CommentAttachment getCommentAttachment(long commentId, String name) {
        return commentAttachmentRepo.findByCommentIdAndName(commentId, name);
    }
}
