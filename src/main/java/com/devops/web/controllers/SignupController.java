package com.devops.web.controllers;

import com.devops.backend.persistence.domain.backend.Plan;
import com.devops.backend.persistence.domain.backend.Role;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.backend.persistence.domain.backend.UserRole;
import com.devops.backend.service.PlanService;
import com.devops.backend.service.contract.IUserService;
import com.devops.enums.PlanEnum;
import com.devops.enums.RoleEnum;
import com.devops.utils.UserUtils;
import com.devops.web.domain.frontend.BasicAccountPayload;
import com.devops.web.domain.frontend.ProAccountPayload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

/**
 * Created by ALadin Zaier PC IBS on 23/02/2017.
 */
@Controller
public class SignupController {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_VIEW_NAME = "/registration/signup";
    public static final String SIGNUP_URL_MAPPING = "/signup";

    private static final String SIGNUP_PAYLOAD_MODEL_KEY = "payload";

    private static final String SIGNED_UP_MESSAGE_KEY = "signedUp";

    private static final String DUPLICATED_USERNAME_KEY = "duplicatedUsername";
    private static final String DUPLICATED_EMAIL_KEY = "duplicatedEmail";

    private static final String ERROR_MESSAGE_KEY = "message";


    @Autowired
    private PlanService planService;

    @Autowired
    private IUserService userService;

    @GetMapping(SIGNUP_URL_MAPPING)
    public String getSignupPage(@RequestParam("planId") int planId, Model model) {

        if (planId != PlanEnum.BASIC.getId() && planId != PlanEnum.PRO.getId()) {
            throw new IllegalArgumentException();
        }

        model.addAttribute(SIGNUP_PAYLOAD_MODEL_KEY, new ProAccountPayload());
        model.addAttribute("registred", "false");

        return SIGNUP_VIEW_NAME;
    }

    @PostMapping(SIGNUP_URL_MAPPING)
    public String accountRegistration(@RequestParam(name = "planId", required = true) int planId,
                                      @ModelAttribute(SIGNUP_PAYLOAD_MODEL_KEY) @Valid ProAccountPayload payload,
                                      ModelMap model) throws IOException {

        if (planId != PlanEnum.BASIC.getId() && planId != PlanEnum.PRO.getId()) {
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id does not exist");
            return SIGNUP_VIEW_NAME;
        }

        this.checkForDuplicates(payload, model);

        boolean duplicates = false;

        List<String> errorMessages = new ArrayList<>();

        if (model.containsKey(DUPLICATED_USERNAME_KEY)) {
            LOG.warn("The username already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            errorMessages.add("Username already exist");
            duplicates = true;
        }

        if (model.containsKey(DUPLICATED_EMAIL_KEY)) {
            LOG.warn("The email already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            errorMessages.add("Email already exist");
            duplicates = true;
        }

        if (duplicates) {
            model.addAttribute(ERROR_MESSAGE_KEY, errorMessages);
            return SIGNUP_VIEW_NAME;
        }


        // There are certain info that the user doesn't set, such as profile image URL, Stripe customer id,
        // plans and roles
        LOG.debug("Transforming user payload into User domain object");
        User user = UserUtils.fromWebUserToDomainUser(payload);


        // Sets the Plan and the Roles (depending on the chosen plan)
        LOG.debug("Retrieving plan from the database");
        Plan selectedPlan = planService.findPlanById(planId);
        if (null == selectedPlan) {
            LOG.error("The plan id {} could not be found. Throwing exception.", planId);
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id not found");
            return SIGNUP_VIEW_NAME;
        }
        user.setPlan(selectedPlan);

        User registeredUser = null;


        // By default users get the BASIC ROLE
        Set<UserRole> roles = new HashSet<>();
        if (planId == PlanEnum.BASIC.getId()) {
            roles.add(new UserRole(user, new Role(RoleEnum.BASIC)));
            registeredUser = userService.createUser(user, PlanEnum.BASIC, roles);
        } else {
            roles.add(new UserRole(user, new Role(RoleEnum.PRO)));
            registeredUser = userService.createUser(user, PlanEnum.PRO, roles);
            LOG.debug(payload.toString());
        }
            // Auto logins the registered user
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    registeredUser, null, registeredUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            LOG.info("User created successfully");

            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "true");

            return SIGNUP_VIEW_NAME;


    }

    /* Private Methods */

    private void checkForDuplicates(BasicAccountPayload payload, ModelMap model) {

        // Username
        if (userService.findByUserName(payload.getUsername()) != null) {
            model.addAttribute(DUPLICATED_USERNAME_KEY, true);
        }
        if (userService.findByEmail(payload.getEmail()) != null) {
            model.addAttribute(DUPLICATED_EMAIL_KEY, true);
        }

    }

}
