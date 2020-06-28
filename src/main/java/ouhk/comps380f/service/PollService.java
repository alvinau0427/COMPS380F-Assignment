package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import ouhk.comps380f.exception.PollNotFound;
import ouhk.comps380f.model.Poll;

public interface PollService {
    
    public List<Poll> getPolls();
    
    public Poll getPoll(long id);
    
    public Poll getLastPoll();
    
    public long getPollCount();
    
    public long createPoll(String userName, String question, String answer1, String answer2, String answer3, String answer4, Date timestamp) throws IOException;
    
    public void updatePoll(long id, String question, String answer1, String answer2, String answer3, String answer4, Date timestamp) throws IOException, PollNotFound;
    
    public void deletePoll(long id) throws PollNotFound;
}
