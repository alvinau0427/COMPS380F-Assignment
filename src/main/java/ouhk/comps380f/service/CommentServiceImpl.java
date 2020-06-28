package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.CommentRepository;
import ouhk.comps380f.exception.CommentNotFound;
import ouhk.comps380f.model.Comment;
import ouhk.comps380f.model.CommentAttachment;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepo;

    @Override
    @Transactional
    public List<Comment> getComment(long topicId) {
        List<Comment> list = commentRepo.findByTopicId(topicId);
        return list;
    }

    @Override
    @Transactional
    public long createComment(String userName, String commentdt, Date timestamp, long topicId, List<MultipartFile> attachments) throws IOException {
        Comment comment = new Comment();
        comment.setUserName(userName);
        comment.setCommentdt(commentdt);
        comment.setTimestamp(timestamp);
        comment.setTopicId(topicId);
        for (MultipartFile filePart : attachments) {
            CommentAttachment attachment = new CommentAttachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setComment(comment);
            if (attachment.getName() != null && attachment.getName().length() > 0 && attachment.getContents() != null && attachment.getContents().length > 0) {
                comment.getAttachments().add(attachment);
            }
        }
        Comment SavedComment = commentRepo.save(comment);
        return SavedComment.getTopicId();
    }

    @Override
    @Transactional(rollbackFor = CommentNotFound.class)
    public void deleteComment(long topicId, long id) throws CommentNotFound {
        Comment deletedComment = commentRepo.findByTopicIdAndId(topicId, id);
        if (deletedComment.getId() == id) {
            commentRepo.delete(deletedComment);
        }
    }

    @Override
    @Transactional(rollbackFor = CommentNotFound.class)
    public void deleteCommenttp(long topicId) throws CommentNotFound {
        List<Comment> deletedComment2 = commentRepo.findByTopicId(topicId);
        commentRepo.deleteAll(deletedComment2);
    }
}
