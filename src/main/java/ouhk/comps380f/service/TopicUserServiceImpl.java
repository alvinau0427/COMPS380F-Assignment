package ouhk.comps380f.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.TopicUserRepository;
import ouhk.comps380f.exception.TopicUserNotFound;
import ouhk.comps380f.model.TopicUser;
import ouhk.comps380f.model.UserRole;

@Service
public class TopicUserServiceImpl implements UserDetailsService, TopicUserService {

    @Resource
    TopicUserRepository topicUserRepo;
    
    @Override
    @Transactional
    public List<TopicUser> getTopicUsers() {
        return topicUserRepo.findAll();
    }
    
    @Override
    @Transactional
    public TopicUser getTopicUser(String username) {
        return topicUserRepo.findByUsername(username);
    }
    
    @Override
    @Transactional
    public long getUserCount() {
        return topicUserRepo.findAll().stream().count();
    }
    
    @Override
    @Transactional
    public void registerTopicUser(String username, String password, String[] roles) throws IOException {
        TopicUser topicUser = new TopicUser();
        List<UserRole> userRole = new ArrayList<>();
        for (String role : roles) {
            userRole.add(new UserRole(topicUser, role));
        }
        topicUser.setUsername(username);
        topicUser.setPassword(password);
        topicUser.setRoles(userRole);
        topicUserRepo.save(topicUser);
    }
    
    @Override
    @Transactional
    public void createTopicUser(String username, String password, String[] roles) throws IOException {
        TopicUser topicUser = new TopicUser();
        List<UserRole> userRole = new ArrayList<>();
        for (String role : roles) {
            userRole.add(new UserRole(topicUser, role));
        }
        topicUser.setUsername(username);
        topicUser.setPassword(password);
        topicUser.setRoles(userRole);
        topicUserRepo.save(topicUser);
    }
    
    @Override
    @Transactional
    public void updateTopicUser(String username, String password, String[] roles) throws IOException, TopicUserNotFound {
        TopicUser updatedTopicUser = topicUserRepo.findByUsername(username);
        List<UserRole> userRole = new ArrayList<>();
        for (String role : roles) {
            userRole.add(new UserRole(updatedTopicUser, role));
        }
        if (updatedTopicUser == null) {
            throw new TopicUserNotFound();
        }
        updatedTopicUser.setUsername(username);
        updatedTopicUser.setPassword(password);
        updatedTopicUser.setRoles(userRole);
        topicUserRepo.save(updatedTopicUser);
    }
    
    @Override
    @Transactional(rollbackFor = TopicUserNotFound.class)
    public void deleteTopicUser(String username) throws TopicUserNotFound {
        TopicUser deletedTopicUser = topicUserRepo.findById(username).orElse(null);
        if (deletedTopicUser == null) {
            throw new TopicUserNotFound();
        }
        topicUserRepo.delete(deletedTopicUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TopicUser topicUser = topicUserRepo.findById(username).orElse(null);
        if (topicUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : topicUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new User(topicUser.getUsername(), topicUser.getPassword(), authorities);
    }
}
