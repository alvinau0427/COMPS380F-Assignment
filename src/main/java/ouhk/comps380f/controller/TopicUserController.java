package ouhk.comps380f.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.exception.TopicUserNotFound;
import ouhk.comps380f.model.TopicUser;
import ouhk.comps380f.model.UserRole;
import ouhk.comps380f.service.TopicUserService;

@Controller
@RequestMapping("/user")
public class TopicUserController {
    
    private final Logger logger = LogManager.getLogger(this.getClass());
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TopicUserService topicUserService;

    @GetMapping({"", "/*", "/list"})
    public String list(ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/topic/index";
        } else {
            model.addAttribute("username", principal.getName());
            model.addAttribute("count", topicUserService.getUserCount());
            model.addAttribute("topicUsers", topicUserService.getTopicUsers());
            return "listUser";
        }
    }

    @GetMapping("/register")
    public ModelAndView showRegister(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_USER")) {
            return new ModelAndView(new RedirectView("/topic/list", true));
        } else {
            return new ModelAndView("register", "topicUser", new Form());
        }
    }

    @PostMapping("/register")
    public View register(Form form, HttpServletResponse response) throws IOException {
        if (topicUserService.getTopicUser(form.getUsername()) == null) {
            topicUserService.createTopicUser(form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRoles());
            logger.info("User " + form.getUsername() + " registered.");
            return new RedirectView("/login", true);
        } else {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.append("<!DOCTYPE html>\r\n")
                    .append("<html>\r\n")
                    .append("<head>\r\n")
                    .append("<title>Course Discussion Forum</title>\r\n")
                    .append("</head>\r\n")
                    .append("<body>\r\n")
                    .append("<h2>Registeration</h2>\r\n")
                    .append("<i>The username " + form.getUsername() + " is already registered, please try again.</i><br><br />\r\n")
                    .append("<a href='../user/register'>Return to account register page</a>\r\n")
                    .append("</body>\r\n")
                    .append("</html>\r\n");
            return null;
        }
    }

    @GetMapping("/create")
    public ModelAndView showCreate(ModelMap model, Principal principal, HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        }else {
            model.addAttribute("username", principal.getName());
            return new ModelAndView("addUser", "topicUserForm", new Form());
        }
    }

    @PostMapping("/create")
    public View create(Form form, HttpServletResponse response) throws IOException {
        if (topicUserService.getTopicUser(form.getUsername()) == null) {
            topicUserService.createTopicUser(form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRoles());
            logger.info("User " + form.getUsername() + " created.");
            return new RedirectView("/user/list", true);
        } else {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.append("<!DOCTYPE html>\r\n")
                    .append("<html>\r\n")
                    .append("<head>\r\n")
                    .append("<title>Course Discussion Forum</title>\r\n")
                    .append("</head>\r\n")
                    .append("<body>\r\n")
                    .append("<h2>Create user</h2>\r\n")
                    .append("<i>The username " + form.getUsername() + " is already registered, please try again.</i><br><br />\r\n")
                    .append("<a href='../user/create'>Return to create user page</a>\r\n")
                    .append("</body>\r\n")
                    .append("</html>\r\n");
            return null;
        }
    }
    
   @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username, ModelMap model, Principal principal, HttpServletRequest request) {
        TopicUser topicUser = topicUserService.getTopicUser(username);
        if (topicUser == null || !request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView(new RedirectView("/topic/index", true));
        }else {
            model.addAttribute("username", principal.getName());
            model.addAttribute("topicUser", topicUser);
            ModelAndView modelAndView = new ModelAndView("editUser");
            modelAndView.addObject("topicUser", topicUser);
            Form topicUserForm = new Form();
            topicUserForm.setUsername(topicUser.getUsername());
            List<UserRole> roles = topicUser.getRoles();
            String[] userRole = new String[roles.size()];
            int index = 0;
            for (Object value : roles) {
                userRole[index] = String.valueOf(value);
                index++;
            }
            topicUserForm.setRoles(userRole);
            modelAndView.addObject("topicUserForm", topicUserForm);
            return modelAndView;
        }
    }

    @PostMapping("/edit/{username}")
    public View edit(@PathVariable("username") String username, Form form, Principal principal, HttpServletRequest request) throws IOException, TopicUserNotFound {
        TopicUser topicUser = topicUserService.getTopicUser(username);
        if (topicUser == null || !request.isUserInRole("ROLE_ADMIN")) {
            return new RedirectView("/topic/index", true);
        } else {
            topicUserService.updateTopicUser(form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getRoles());
            logger.info("User " + form.getUsername() + " updated.");
            return new RedirectView("/user/list", true);
        }
        
    }

    @GetMapping("/delete/{username}")
    public View deleteTopicUser(@PathVariable("username") String username) throws TopicUserNotFound {
        topicUserService.deleteTopicUser(username);
        logger.info("User " + username + " deleted.");
        return new RedirectView("/user/list", true);
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;
        
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }
}
