package com.devops;

import com.devops.backend.service.I18NService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DevopsApplicationTests {

	@Autowired
	I18NService i18NService;

	@Test
	public void i18nService_should_return_right_message_from_an_idMessage() {
		String messageId = "index.main.collout";
		String messageValue = "Bootstrap starter template";
		Assert.assertEquals(messageValue, i18NService.getMessage(messageId));
	}

}
