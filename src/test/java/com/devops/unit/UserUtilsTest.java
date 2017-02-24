package com.devops.unit;

import com.devops.AbstractTest;
import com.devops.backend.persistence.domain.backend.User;
import com.devops.utils.UserUtils;
import com.devops.web.controllers.ForgotmyPasswordController;
import com.devops.web.domain.frontend.BasicAccountPayload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.mock.web.MockHttpServletRequest;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 21/02/2017.
 */
public class UserUtilsTest extends AbstractTest {

    private static final long dummyUserId = 1234;
    private PodamFactory podamFactory;

    @Rule
    public TestName testName = new TestName();

    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    public void testPasswordResetEmailUrlConstruction() {

        mockHttpServletRequest.setServerPort(8080);
        String token = UUID.randomUUID().toString();
        String expectedUrl = "http://localhost:8080" +
                ForgotmyPasswordController.CHANGE_PASSWORD_URL + "?id=" + dummyUserId + "&token=" + token;
        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, dummyUserId, token);
        Assert.assertEquals(expectedUrl, actualUrl);
    }


    @Test
    public void testFromWebUserToDomainUser(){
        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail(testName.getMethodName()+"@tutsviews.fr");

        User user = UserUtils.fromWebUserToDomainUser(webUser);

        Assert.assertEquals(webUser.getUsername(), user.getUsername());
        Assert.assertEquals(webUser.getPassword(), user.getPassword());
        Assert.assertEquals(webUser.getFirstName(), user.getFirstName());
        Assert.assertEquals(webUser.getLastName(), user.getLastName());
        Assert.assertEquals(webUser.getEmail(), user.getEmail());
        Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
        Assert.assertEquals(webUser.getCountry(), user.getCountry());
        Assert.assertEquals(webUser.getDescription(), user.getDescription());


    }

}
