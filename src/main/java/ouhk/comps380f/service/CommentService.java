package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.exception.CommentNotFound;
import ouhk.comps380f.model.Comment;

public interface CommentService {
    public List<Comment> getComment(long topicId);
    
    public long createComment(String userName, String commentdt, Date timestamp, long topicId, List<MultipartFile> attachments) throws IOException;
    
    public void deleteComment(long id,long topicId) throws CommentNotFound;

    public void deleteCommenttp(long topicId) throws CommentNotFound;
}
