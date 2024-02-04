package karate;

import org.springframework.boot.test.context.SpringBootTest;

import io.github.mehdicharife.missionauthservice.MissionAuthServiceApplication;

import com.intuit.karate.junit5.Karate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {MissionAuthServiceApplication.class})
public class KarateTests {
    

    @Karate.Test
    public Karate registrationRequestCreationTest() {
        return Karate.run("classpath:karate/registration_request/create.feature");
    }
}
