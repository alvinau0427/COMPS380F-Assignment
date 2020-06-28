package ouhk.comps380f.service;

import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.CommentAttachment;

public interface AttachmentService {

    public Attachment getAttachment(long topicId, String name);
    
    public CommentAttachment getCommentAttachment(long commentId, String name);
}
