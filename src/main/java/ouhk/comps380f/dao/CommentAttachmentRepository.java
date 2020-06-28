package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.CommentAttachment;

public interface CommentAttachmentRepository extends JpaRepository<CommentAttachment, Long> {

    public CommentAttachment findByCommentIdAndName(long commentId, String name);
}
