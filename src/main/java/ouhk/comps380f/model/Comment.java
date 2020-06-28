package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String userName;

    @Column(name = "commentdt")
    private String commentdt;

    private Date timestamp;

    @Column(name = "topic_id")
    private long topicId;
    
    @OneToMany(mappedBy = "comment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<CommentAttachment> attachments = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentdt() {
        return commentdt;
    }

    public void setCommentdt(String commentdt) {
        this.commentdt = commentdt;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        timestamp = new Date();
        this.timestamp = timestamp;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }
    
    public List<CommentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CommentAttachment> attachments) {
        this.attachments = attachments;
    }
    
    public void deleteAttachment(CommentAttachment attachment) {
        attachment.setComment(null);
        this.attachments.remove(attachment);
    }
}
