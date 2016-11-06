package com.wadeyuan.training.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.wadeyuan.training.common.Constants;
import com.wadeyuan.training.entity.User;
import com.wadeyuan.training.exception.ParameterException;
import com.wadeyuan.training.exception.ServiceException;
import com.wadeyuan.training.service.UserService;

@Controller
@RequestMapping("/user")
@SessionAttributes(Constants.USER)
public class UserController {

    private static final String LOGIN_PAGE = "login";
    private static final String LIST_BOOK_PAGE = "book/list";
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext severletContext;

    /**
     * Show login page
     */
    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public ModelAndView login(@RequestParam(value="redirect_to", defaultValue="") String redirectTo, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject(Constants.REDIRECT_TO, redirectTo);

        if(model.asMap().get(Constants.USER) == null) {
            modelAndView.setViewName(LOGIN_PAGE);
            logger.debug("Loading login page");
            return modelAndView;
        } else {
            User user = (User) model.asMap().get(Constants.USER);
            logger.debug(String.format("User logged in as [%s], redirecting to [%s]", user.getUsername(), redirectTo));
            return sendRedirectAfterLogin(redirectTo, severletContext.getContextPath(), modelAndView);
        }
    }

    /**
     * Perform the login action
     * @return
     */
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ModelAndView loginSubmit(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "redirect_to", defaultValue = "") String redrectTo,
            Model model) {


        User user = null;
        ModelAndView modelAndView = new ModelAndView();

        try {
            user = userService.login(username, password);
        } catch (ParameterException pe) {
            modelAndView.addObject(Constants.ERROR_MESSAGE, "Username or password is invalid");
            modelAndView.setViewName(LOGIN_PAGE);
            return modelAndView;
        } catch (ServiceException se) {
            modelAndView.addObject(Constants.ERROR_MESSAGE, se.getMessage());
            modelAndView.setViewName(LOGIN_PAGE);
            return modelAndView;
        }

        user.setPassword(null);
        model.addAttribute(Constants.USER, user);
        logger.info(String.format("Logged in with username: [%s]", username));
        return sendRedirectAfterLogin(redrectTo, severletContext.getContextPath(), modelAndView);
    }

    @RequestMapping(value = "/logout", method=RequestMethod.GET)
    public ModelAndView logout(Model model, SessionStatus sessionStatus) {
        ModelAndView modelAndView = new ModelAndView();
        sessionStatus.setComplete(); // Set session status as complete, clear "User" object stored in annotation @SessionAttributes
        modelAndView.setViewName(LOGIN_PAGE);
        return modelAndView;
    }

    private ModelAndView sendRedirectAfterLogin(String redirectTo, String contextPath, ModelAndView modelAndView){
        String redirectViewPath = contextPath + "/";
        if(redirectTo == null || redirectTo.isEmpty()) {
            redirectViewPath += LIST_BOOK_PAGE;
        } else {
            try {
                redirectViewPath += URLDecoder.decode(redirectTo, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        modelAndView.setView(new RedirectView(redirectViewPath));
        return modelAndView;
    }
}
