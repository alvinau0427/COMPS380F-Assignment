package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.TopicRepository;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TopicNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Topic;

@Service
public class TopicServiceImpl implements TopicService {
    
    @Resource
    private TopicRepository topicRepo;

    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Topic> getTopics() {
        return topicRepo.findAll();
    }
    
    @Override
    @Transactional
    public List<Topic> getCategoryTopics(String category) {
        List<Topic> list = topicRepo.findByCategory(category);
        return list;
    }

    @Override
    @Transactional
    public Topic getTopic(long id) {
        return topicRepo.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public long getTopicCount() {
        return topicRepo.findAll().stream().count();
    }
    
    @Override
    @Transactional
    public long getCategoryTopicCount(String category) {
        List<Topic> list = topicRepo.findByCategory(category);
        return list.stream().count();
    }
    
    @Override
    @Transactional
    public long createTopic(String userName, String title, String content, String category, Date timestamp, List<MultipartFile> attachments) throws IOException {
        Topic topic = new Topic();
        topic.setUserName(userName);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCategory(category);
        topic.setTimestamp(timestamp);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTopic(topic);
            if (attachment.getName() != null && attachment.getName().length() > 0 && attachment.getContents() != null && attachment.getContents().length > 0) {
                topic.getAttachments().add(attachment);
            }
        }
        Topic savedTopic = topicRepo.save(topic);
        return savedTopic.getId();
    }
    
    @Override
    @Transactional(rollbackFor = TopicNotFound.class)
    public void updateTopic(long id, String title, String content, String category, Date timestamp, List<MultipartFile> attachments) throws IOException, TopicNotFound {
        Topic updatedTopic = topicRepo.findById(id).orElse(null);
        if (updatedTopic == null) {
            throw new TopicNotFound();
        }
        updatedTopic.setTitle(title);
        updatedTopic.setContent(content);
        updatedTopic.setCategory(category);
        updatedTopic.setTimestamp(timestamp);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTopic(updatedTopic);
            if (attachment.getName() != null && attachment.getName().length() > 0 && attachment.getContents() != null && attachment.getContents().length > 0) {
                updatedTopic.getAttachments().add(attachment);
            }
        }
        topicRepo.save(updatedTopic);
    }

    @Override
    @Transactional(rollbackFor = TopicNotFound.class)
    public void deleteTopic(long id) throws TopicNotFound {
        Topic deletedTopic = topicRepo.findById(id).orElse(null);
        if (deletedTopic == null) {
            throw new TopicNotFound();
        }
        topicRepo.delete(deletedTopic);
    }

    @Override
    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long topicId, String name) throws AttachmentNotFound {
        Topic topic = topicRepo.findById(topicId).orElse(null);
        for (Attachment attachment : topic.getAttachments()) {
            if (attachment.getName().equals(name)) {
                topic.deleteAttachment(attachment);
                topicRepo.save(topic);
                return;
            }
        }
        throw new AttachmentNotFound();
    }
}
