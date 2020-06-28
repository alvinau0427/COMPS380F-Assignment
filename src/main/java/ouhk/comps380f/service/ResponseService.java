package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import ouhk.comps380f.exception.ResponseNotFound;
import ouhk.comps380f.model.Response;

public interface ResponseService {
    
    public List<Response> getResponses();
    
    public List<Response> getPollResponses(long id);
    
    public List<Response> getChoiceResponses(String choice);
    
    public long getResponseCount(long id);
    
    public long getChoiceCount(long id, String choice);
    
    public List<Response> checkPoll(long id, String username);
    
    public void createResponse(String userName, String question, String choice, Date timestamp, long pollId) throws IOException;
    
    public void deleteAllResponse(long id) throws ResponseNotFound;
}
