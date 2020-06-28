package ouhk.comps380f.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import static org.hibernate.criterion.Projections.property;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ouhk.comps380f.dao.PollRepository;
import ouhk.comps380f.exception.PollNotFound;
import ouhk.comps380f.model.Poll;

@Service
public class PollServiceImpl implements PollService {

    @Resource
    private PollRepository pollRepo;

    @Override
    @Transactional
    public List<Poll> getPolls() {
        return pollRepo.findAll();
    }
    
    @Override
    @Transactional
    public Poll getPoll(long id) {
        return pollRepo.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public Poll getLastPoll() {
            return pollRepo.findAll().get((pollRepo.findAll()).size() - 1);
    }

    @Override
    @Transactional
    public long getPollCount() {
        return pollRepo.findAll().stream().count();
    }

    @Override
    @Transactional
    public long createPoll(String userName, String question, String answer1, String answer2, String answer3, String answer4, Date timestamp) throws IOException {
        Poll poll = new Poll();
        poll.setUserName(userName);
        poll.setQuestion(question);
        poll.setAnswer1(answer1);
        poll.setAnswer2(answer2);
        poll.setAnswer3(answer3);
        poll.setAnswer4(answer4);
        poll.setTimestamp(timestamp);
        Poll savedPoll = pollRepo.save(poll);
        return savedPoll.getId();
    }

    @Override
    @Transactional(rollbackFor = PollNotFound.class)
    public void updatePoll(long id, String question, String answer1, String answer2, String answer3, String answer4, Date timestamp) throws IOException, PollNotFound {
        Poll updatedPoll = pollRepo.findById(id).orElse(null);
        if (updatedPoll == null) {
            throw new PollNotFound();
        }
        updatedPoll.setQuestion(question);
        updatedPoll.setAnswer1(answer1);
        updatedPoll.setAnswer2(answer2);
        updatedPoll.setAnswer3(answer3);
        updatedPoll.setAnswer4(answer4);
        updatedPoll.setTimestamp(timestamp);
        pollRepo.save(updatedPoll);
    }

    @Override
    @Transactional(rollbackFor = PollNotFound.class)
    public void deletePoll(long id) throws PollNotFound {
        Poll deletedPoll = pollRepo.findById(id).orElse(null);
        if (deletedPoll == null) {
            throw new PollNotFound();
        }
        pollRepo.delete(deletedPoll);
    }
}
