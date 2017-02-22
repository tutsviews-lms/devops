package com.devops.unit;

import com.devops.AbstractTest;
import com.devops.utils.UserUtils;
import com.devops.web.controllers.ForgotmyPasswordController;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

/**
 * Created by ALadin Zaier PC IBS on 21/02/2017.
 */
public class UserUtilsTest extends AbstractTest{

    private static final long dummyUserId = 1234;

    private MockHttpServletRequest mockHttpServletRequest;

    @Before
    public void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    public void testPasswordResetEmailUrlConstruction(){

        mockHttpServletRequest.setServerPort(8080);

        String token = UUID.randomUUID().toString();

        String expectedUrl = "http://localhost:8080"+
                ForgotmyPasswordController.CHANGE_PASSWORD_URL+"?id="+dummyUserId+"&token="+token;

        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest,dummyUserId,token);


        Assert.assertEquals(expectedUrl,actualUrl);
    }



}
