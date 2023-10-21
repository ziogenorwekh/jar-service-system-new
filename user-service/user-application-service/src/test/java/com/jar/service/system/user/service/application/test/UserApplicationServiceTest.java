package com.jar.service.system.user.service.application.test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = UserApplicationTestConfiguration.class)
public class UserApplicationServiceTest {



}
