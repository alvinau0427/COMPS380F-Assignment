package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.ResponseRepository;
import ouhk.comps380f.exception.ResponseNotFound;
import ouhk.comps380f.model.Response;

@Service
public class ResponseServiceImpl implements ResponseService {
    
    @Resource
    private ResponseRepository responseRepo;

    @Override
    @Transactional
    public List<Response> getResponses() {
        return responseRepo.findAll();
    }
     
    @Override
    @Transactional
    public List<Response> getPollResponses(long id) {
        List<Response> list = responseRepo.findByPollId(id);
        return list;
    }
    
    @Override
    @Transactional
    public List<Response> getChoiceResponses(String choice) {
        List<Response> list = responseRepo.findByChoice(choice);
        return list;
    }
    
    @Override
    @Transactional
    public long getResponseCount(long id) {
        List<Response> list = responseRepo.findByPollId(id);
        return list.stream().count();
    }
    
    @Override
    @Transactional
    public long getChoiceCount(long id, String choice) {
        List<Response> list = responseRepo.findByPollIdAndChoice(id, choice);
        return list.stream().count();
    }

    @Override
    @Transactional
    public List<Response> checkPoll(long id, String username) {
        List<Response> list = responseRepo.findByPollIdAndUserName(id, username);
        return list;
    }
    
    @Override
    @Transactional
    public void createResponse(String userName, String question, String choice, Date timestamp, long pollId) throws IOException {
        Response response = new Response();
        response.setUserName(userName);
        response.setQuestion(question);
        response.setChoice(choice);
        response.setTimestamp(timestamp);
        response.setPollId(pollId);
        responseRepo.save(response);
    }
    
    @Override
    @Transactional(rollbackFor = ResponseNotFound.class)
    public void deleteAllResponse(long id) throws ResponseNotFound {
        List<Response> list = responseRepo.findByPollId(id);
        responseRepo.deleteAll(list);
    }
}
