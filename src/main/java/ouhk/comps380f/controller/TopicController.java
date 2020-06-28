package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.CommentNotFound;
import ouhk.comps380f.exception.TopicNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.CommentAttachment;
import ouhk.comps380f.model.Poll;
import ouhk.comps380f.model.Topic;
import ouhk.comps380f.service.AttachmentService;
import ouhk.comps380f.service.CommentService;
import ouhk.comps380f.service.PollService;
import ouhk.comps380f.service.ResponseService;
import ouhk.comps380f.service.TopicService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private AttachmentService attachmentService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private PollService pollService;
    
    @Autowired
    private ResponseService responseService;
    
    @GetMapping({"", "/*", "/index"})
    public ModelAndView showIndex(ModelMap model, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("pollDatabase", pollService.getPolls());
        
        long count = pollService.getPollCount();
        if (count != 0) {
            Poll poll = pollService.getLastPoll();
            
            if (request.isUserInRole("ROLE_USER")) {
                model.addAttribute("check", responseService.checkPoll(poll.getId(), principal.getName()));
            }
            model.addAttribute("poll", pollService.getLastPoll());
            model.addAttribute("count", responseService.getResponseCount(poll.getId()));
            model.addAttribute("answer1Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer1()));
            model.addAttribute("answer2Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer2()));
            model.addAttribute("answer3Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer3()));
            model.addAttribute("answer4Count", responseService.getChoiceCount(poll.getId(), poll.getAnswer4()));
            return new ModelAndView("index", "pollForm", new Form3());
        } else {
            return new ModelAndView("index");
        }
    }
    
    @PostMapping("/index")
    public View addComment(Form3 form3, Principal principal) throws IOException {
        Poll poll = pollService.getLastPoll();
        if (poll == null) {
            return new RedirectView("/topic/index", true);
        }
        responseService.createResponse(principal.getName(), form3.getQuestion(), form3.getChoice(), form3.getTimestamp(), form3.getPollId());
        return new RedirectView(("/topic/index/"), true);
    }

    @GetMapping("/list")
    public String list(ModelMap model, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("categories", "All");
        model.addAttribute("count", topicService.getTopicCount());
        model.addAttribute("topicDatabase", topicService.getTopics());
        return "listTopic";
    }
    
    @GetMapping("/lecture")
    public String showLecture(ModelMap model, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName()); 
        }
        model.addAttribute("categories", "Lecture");
        model.addAttribute("count", topicService.getCategoryTopicCount("lecture"));
        model.addAttribute("topicDatabase", topicService.getCategoryTopics("lecture"));
        return "listTopic";
    }
    
    @GetMapping("/laboratory")
    public String showLaboratory(ModelMap model, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName()); 
        }
        model.addAttribute("categories", "Laboratory");
        model.addAttribute("count", topicService.getCategoryTopicCount("laboratory"));
        model.addAttribute("topicDatabase", topicService.getCategoryTopics("laboratory"));
        return "listTopic";
    }
    
    @GetMapping("/others")
    public String showOthers(ModelMap model, String category, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("categories", "Others");
        model.addAttribute("count", topicService.getCategoryTopicCount("others"));
        model.addAttribute("topicDatabase", topicService.getCategoryTopics("others"));
        return "listTopic";
    }

    @GetMapping("/create")
    public ModelAndView showCreate(ModelMap model, String category, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_USER")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        } else {
            model.addAttribute("username", principal.getName());
            return new ModelAndView("addTopic", "topicForm", new Form());
        }
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long topicId = topicService.createTopic(principal.getName(), form.getTitle(), form.getContent(), form.getCategory(), form.getTimestamp(), form.getAttachments());
        return new RedirectView(("/topic/view/" + topicId), true);
    }

    @GetMapping("/view/{topicId}")
    public String view(@PathVariable("topicId") long topicId, ModelMap model, Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("username", principal.getName());
        }
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            return "redirect:/topic/list";
        }
        model.addAttribute("topic", topic);
        model.addAttribute("comment", commentService.getComment(topicId));
        return "viewTopic";
    }
    
    @GetMapping("/edit/{topicId}")
    public ModelAndView showEdit(@PathVariable("topicId") long topicId, ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_USER")) {
            return new ModelAndView(new RedirectView("/topic/list", true));
        } else {
            model.addAttribute("username", principal.getName());
            Topic topic = topicService.getTopic(topicId);
            if (topic == null || (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(topic.getUserName()))) {
                return new ModelAndView(new RedirectView("/topic/" + topic.getCategory(), true));
            }
            ModelAndView modelAndView = new ModelAndView("editTopic");
            modelAndView.addObject("topic", topic);
            Form topicForm = new Form();
            topicForm.setUsername(topic.getUserName());
            topicForm.setCategory(topic.getCategory());
            topicForm.setTitle(topic.getTitle());
            topicForm.setContent(topic.getContent());
            modelAndView.addObject("topicForm", topicForm);
            return modelAndView;
        } 
    }

    @PostMapping("/edit/{topicId}")
    public View edit(@PathVariable("topicId") long topicId, String category, Form form, Principal principal, HttpServletRequest request) throws IOException, TopicNotFound {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null || (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(topic.getUserName()))) {
            return new RedirectView("/topic/list", true);
        }
        if(form.getCategory() == null) {
            category = topic.getCategory();
        }else {
            category = form.getCategory();
        }
        topicService.updateTopic(topicId, form.getTitle(), form.getContent(), category, form.getTimestamp(), form.getAttachments());
        return new RedirectView(("/topic/view/" + topicId), true);
    }

    @GetMapping("/{topicId}/attachment/{attachment:.+}")
    public View download(@PathVariable("topicId") long topicId, @PathVariable("attachment") String name) {
        Attachment attachment = attachmentService.getAttachment(topicId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/topic/list", true);
    }
    
    @GetMapping("/comment/{commentId}/attachment/{attachment:.+}")
    public View commentDownload(@PathVariable("commentId") long commentId, @PathVariable("attachment") String name) {
        CommentAttachment attachment = attachmentService.getCommentAttachment(commentId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/topic/list", true);
    }

    @GetMapping("/{topicId}/delete/{attachment:.+}")
    public View deleteAttachment(@PathVariable("topicId") long topicId, @PathVariable("attachment") String name) throws AttachmentNotFound {
        topicService.deleteAttachment(topicId, name);
        return new RedirectView(("/topic/edit/" + topicId), true);
    }

    @GetMapping("/delete/{topicId}")
    public View deleteTopic(@PathVariable("topicId") long topicId) throws CommentNotFound, TopicNotFound, IOException {
        commentService.deleteCommenttp(topicId);  
        topicService.deleteTopic(topicId);
        return new RedirectView("/topic/list", true);
    }
    
    @GetMapping("/comment/{topicId}")
    public ModelAndView showAddComment(@PathVariable("topicId") long topicId, ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_USER")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        } else {
            model.addAttribute("username", principal.getName());
            Topic topic = topicService.getTopic(topicId);
            if (topic == null) {
                return new ModelAndView(new RedirectView("/topic/list", true));
            }
            model.addAttribute("topic", topic);
            model.addAttribute("username", principal.getName());
            return new ModelAndView("addComment", "commentForm", new Form2());
        } 
    }
    
    @PostMapping("/comment/{topicId}")
    public View addComment(long topicId, Form2 form2, Principal principal) throws IOException {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            return new RedirectView("/topic/list", true);
        }
        commentService.createComment(form2.getUserName(), form2.getCommentdt(), form2.getTimestamp(), form2.getTopicId(), form2.getAttachments());
        return new RedirectView(("/topic/view/" + topicId), true);
    }
    
    @GetMapping("/delete/comment/{topicId}/{Id}")
    public View deleteComment(@PathVariable("topicId") long topicId, @PathVariable("Id") long Id) throws CommentNotFound {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            return new RedirectView("/topic/list", true);
        }
        commentService.deleteComment(topicId, Id);
        return new RedirectView(("/topic/view/" + topicId), true);
    }
    
    public static class Form {

        private String username;
        private String title;
        private String content;
        private String category;
        private Date timestamp;
        private List<MultipartFile> attachments;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            timestamp = new Date();
            this.timestamp = timestamp;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }
    
    public static class Form2 {
        
        private String userName;
        private String commentdt;
        private Date timestamp;
        private long topicId;
        private List<MultipartFile> attachments;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCommentdt() {
            return commentdt;
        }

        public void setCommentdt(String commentdt) {
            this.commentdt = commentdt;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public long getTopicId() {
            return topicId;
        }

        public void setTopicId(long topicId) {          
            this.topicId = topicId;
        }
        
        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }
    
    public static class Form3 {
        
        private String userName;
        private String question;
        private String choice;
        private Date timestamp;
        private long pollId;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getChoice() {
            return choice;
        }

        public void setChoice(String choice) {
            this.choice = choice;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            timestamp = new Date();
            this.timestamp = timestamp;
        }

        public long getPollId() {
            return pollId;
        }

        public void setPollId(long pollId) {
            this.pollId = pollId;
        }  
    }
}
