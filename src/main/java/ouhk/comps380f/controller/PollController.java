package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.exception.PollNotFound;
import ouhk.comps380f.exception.ResponseNotFound;
import ouhk.comps380f.model.Poll;
import ouhk.comps380f.service.PollService;
import ouhk.comps380f.service.ResponseService;

@Controller
@RequestMapping("/poll")
public class PollController {

    @Autowired
    private PollService pollService;
    
    @Autowired
    private ResponseService responseService;

    @GetMapping({"", "/*", "/list"})
    public String list(ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/topic/index";
        } else {
            model.addAttribute("username", principal.getName());
            model.addAttribute("pollDatabase", pollService.getPolls());
            model.addAttribute("count", pollService.getPollCount());
            return "listPoll";
        }  
    }

    @GetMapping("/create")
    public ModelAndView showCreate(ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        } else {
            model.addAttribute("username", principal.getName());
            return new ModelAndView("addPoll", "pollForm", new Form());
        }
    }
    
    @PostMapping("create")
    public View create(Form form, Principal principal) throws IOException {
        long pollId = pollService.createPoll(principal.getName(), form.getQuestion(), form.getAnswer1(), form.getAnswer2(), form.getAnswer3(), form.getAnswer4(), form.getTimestamp());
        return new RedirectView("/poll/view/" + pollId, true);
    }
    
    @GetMapping("/view/{pollId}")
    public String view(@PathVariable("pollId") long pollId, ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/topic/index";
        } else {
            model.addAttribute("username", principal.getName());
            Poll poll = pollService.getPoll(pollId);
            if (poll == null) {
                return "redirect:/poll/list";
            }
            model.addAttribute("poll", poll);
            model.addAttribute("count", responseService.getResponseCount(poll.getId()));
            model.addAttribute("answer1Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer1()));
            model.addAttribute("answer2Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer2()));
            model.addAttribute("answer3Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer3()));
            model.addAttribute("answer4Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer4()));
            model.addAttribute("responseDatabase", responseService.getPollResponses(poll.getId()));
            return "viewPoll";
        }
    }
    
    @GetMapping("/edit/{pollId}")
    public ModelAndView showEdit(@PathVariable("pollId") long pollId, ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        } else {
            model.addAttribute("username", principal.getName());
            Poll poll = pollService.getPoll(pollId);
            if (poll == null || (!request.isUserInRole("ROLE_ADMIN"))) {
                return new ModelAndView(new RedirectView("/poll/list", true));
            }
            ModelAndView modelAndView = new ModelAndView("editPoll");
            modelAndView.addObject("poll", poll);
            Form pollForm = new Form();
            pollForm.setUsername(poll.getUserName());
            pollForm.setQuestion(poll.getQuestion());
            pollForm.setAnswer1(poll.getAnswer1());
            pollForm.setAnswer2(poll.getAnswer2());
            pollForm.setAnswer3(poll.getAnswer3());
            pollForm.setAnswer4(poll.getAnswer4());
            modelAndView.addObject("pollForm", pollForm);
            return modelAndView;
        }
    }
    
    @PostMapping("/edit/{pollId}")
    public View edit(@PathVariable("pollId") long pollId, Form form, Principal principal, HttpServletRequest request) throws IOException, PollNotFound {
        Poll poll = pollService.getPoll(pollId);
        if (poll == null) {
            return new RedirectView("/poll/list", true);
        }
        pollService.updatePoll(pollId, form.getQuestion(), form.getAnswer1(), form.getAnswer2(), form.getAnswer3(), form.getAnswer4(), form.getTimestamp());
        return new RedirectView(("/poll/view/" + pollId), true);
    }
    
    @GetMapping("delete/{pollId}")
    public View deletePoll(@PathVariable("pollId") long pollId) throws ResponseNotFound, PollNotFound, IOException {
        responseService.deleteAllResponse(pollId);
        pollService.deletePoll(pollId);
        return new RedirectView("/poll/list", true);
    }
    
    public static class Form {

        private String username;
        private String question;
        private String answer1;
        private String answer2;
        private String answer3;
        private String answer4;
        private Date timestamp;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer1() {
            return answer1;
        }

        public void setAnswer1(String answer1) {
            this.answer1 = answer1;
        }

        public String getAnswer2() {
            return answer2;
        }

        public void setAnswer2(String answer2) {
            this.answer2 = answer2;
        }

        public String getAnswer3() {
            return answer3;
        }

        public void setAnswer3(String answer3) {
            this.answer3 = answer3;
        }

        public String getAnswer4() {
            return answer4;
        }

        public void setAnswer4(String answer4) {
            this.answer4 = answer4;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            timestamp = new Date();
            this.timestamp = timestamp;
        }
    }
}
