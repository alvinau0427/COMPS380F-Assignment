package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    public List<Response> findByPollId(long id);
    
    public List<Response> findByChoice(String choice);
    
    public List<Response> findByPollIdAndChoice(long id, String choice);
    
    public List<Response> findByPollIdAndUserName(long id, String username);
}
