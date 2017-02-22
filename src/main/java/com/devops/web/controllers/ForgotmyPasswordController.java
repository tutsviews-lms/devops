package com.devops.web.controllers;

import com.devops.backend.persistence.domain.backend.PasswordResetToken;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.service.EmailService;
import com.devops.backend.service.I18NService;
import com.devops.backend.service.PasswordResetTokenService;
import com.devops.backend.service.contract.IUserService;
import com.devops.utils.UserUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Clock;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ALadin Zaier PC IBS on 21/02/2017.
 */
@Controller
public class ForgotmyPasswordController {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ForgotmyPasswordController.class);

    private static final String EMAIL_ADDRESS_VIEW_NAME = "/forgotmypassword/emailform";
    public static final String FORGOT_PASSWORD_URL_MAPPING = "/forgotmypassword";
    private static final String MAIl_SENT_KEY = "mailSent";
    public static final String CHANGE_PASSWORD_URL = "/changeuserpassword";
    public static final String EMAIL_MESSAGE_PROPERTY_NAME = "forgotmypassword.email.text";
    private static final String RESET_PASSWORD_VIEW_MAPPING = "/changeuserpassword";
    private static final String RESET_PASSWORD_VIEW_NAME = "/forgotmypassword/newpasswordform";
    private static final String PASSWORD_RESET_ATTRIBUTE_NAME = "passwordReset";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";


    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    EmailService emailService;

    @Autowired
    I18NService i18NService;

    @Autowired
    IUserService userService;

    @Value("${webmaster.email}")
    private String webmasterEmail;


    @GetMapping(FORGOT_PASSWORD_URL_MAPPING)
    public String getView() {
        return EMAIL_ADDRESS_VIEW_NAME;
    }


    @PostMapping(FORGOT_PASSWORD_URL_MAPPING)
    public String forgotPasswordPost(@ModelAttribute("email") String email, Model model, HttpServletRequest request) {

        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenFromEmail(email);
        if (passwordResetToken != null) {

            LOG.info("Token {} created for email {}", passwordResetToken.getToken(), email);

            User user = passwordResetToken.getUser();
            String token = passwordResetToken.getToken();

            String url = UserUtils.createPasswordResetUrl(request, user.getId(), token);
            LOG.info("url is {}", url);

            String emailText = i18NService.getMessage(EMAIL_MESSAGE_PROPERTY_NAME, request.getLocale());

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setSubject("[TUTSVIEWS-LMS] How to reset your password");
            simpleMailMessage.setFrom(webmasterEmail);
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setText(emailText + "\r\n" + url);

            emailService.sendGenericEmailMessage(simpleMailMessage);

        } else {
            LOG.info("Invalid email");
        }

        model.addAttribute(MAIl_SENT_KEY, "true");

        return EMAIL_ADDRESS_VIEW_NAME;

    }


    @GetMapping(RESET_PASSWORD_VIEW_MAPPING)
    public String getResetPasswordView(@RequestParam long id, @RequestParam String token, Model model) {

        if (StringUtils.isEmpty(token) || id == 0) {
            LOG.error("Invalid userID {} or empty token", id, token);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "invalid user id or token value.");
            return RESET_PASSWORD_VIEW_NAME;
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if (passwordResetToken == null) {
            LOG.error("A token with value {} cannot be found.", token);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token not found");
            return RESET_PASSWORD_VIEW_NAME;
        }

        User user = passwordResetToken.getUser();

        if (!(id == user.getId())) {
            LOG.error("Invalid user with userId {}", id);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Invalid user.");
            return RESET_PASSWORD_VIEW_NAME;
        }

        if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
            LOG.error("Token has expired!");
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token has expired");
            return RESET_PASSWORD_VIEW_NAME;
        }


        model.addAttribute("id", id);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return RESET_PASSWORD_VIEW_NAME;
    }


    @PostMapping(RESET_PASSWORD_VIEW_MAPPING)
    public String changePassword(@ModelAttribute("id") long id, @ModelAttribute("password") String password, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            LOG.error("An unauthenticated user tried to invoke the reset password POST method");
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You're not authorized to do this action!");
            return RESET_PASSWORD_VIEW_NAME;
        }

        User user = (User) auth.getPrincipal();

        if (user.getId() != id) {
            LOG.error("Ahaa ! an authentificated user with id {} tried to change password for another user with id {}!", user.getId(), id);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You're not authorised to perform this action!");
            return RESET_PASSWORD_VIEW_NAME;
        }

        userService.upadateUserPassword(user.getId(),password);

        LOG.error("id is {} ", id);
        LOG.error("password is {} ", password);

        LOG.info("Password successfully updated for user {}", user.getUsername());

        model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "true");

        return RESET_PASSWORD_VIEW_NAME;
    }
}