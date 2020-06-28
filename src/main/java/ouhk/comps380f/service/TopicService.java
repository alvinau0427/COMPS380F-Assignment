package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TopicNotFound;
import ouhk.comps380f.model.Topic;

public interface TopicService {

    public List<Topic> getTopics();
    
    public List<Topic> getCategoryTopics(String category);
    
    public Topic getTopic(long id);
    
    public long getTopicCount();
    
    public long getCategoryTopicCount(String category);

    public long createTopic(String userName, String title, String content, String category, Date timestamp, List<MultipartFile> attachments) throws IOException;

    public void updateTopic(long id, String title, String content, String category, Date timestamp, List<MultipartFile> attachments) throws IOException, TopicNotFound;

    public void deleteTopic(long id) throws TopicNotFound;

    public void deleteAttachment(long topicId, String name) throws AttachmentNotFound;
}
