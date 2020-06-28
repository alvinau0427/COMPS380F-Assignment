package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByTopicId(long topicId);

    public Comment findByTopicIdAndId(long topicId, long id);
}
