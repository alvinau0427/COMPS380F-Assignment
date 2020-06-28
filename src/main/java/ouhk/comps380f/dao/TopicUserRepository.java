package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.TopicUser;

public interface TopicUserRepository extends JpaRepository<TopicUser, String> {

    public TopicUser findByUsername(String username);
}
