package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ouhk.comps380f.exception.TopicUserNotFound;
import ouhk.comps380f.model.TopicUser;

public interface TopicUserService {
    
    public List<TopicUser> getTopicUsers();
    
    public TopicUser getTopicUser(String username);
    
    public long getUserCount();
    
    public void registerTopicUser(String username, String password, String[] roles) throws IOException;
    
    public void createTopicUser(String username, String password, String[] roles) throws IOException;
    
    public void updateTopicUser(String username, String password, String[] roles) throws IOException, TopicUserNotFound;
    
    public void deleteTopicUser(String username) throws TopicUserNotFound;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
