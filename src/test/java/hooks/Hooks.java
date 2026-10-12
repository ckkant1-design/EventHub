package hooks;



import auth.TokenManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void beforeScenario() {

        TokenManager.clearToken();

        System.out.println(
                "===== START SCENARIO ====="
        );
    }

    @After
    public void afterScenario() {

        TokenManager.clearToken();

        System.out.println(
                "===== END SCENARIO ====="
        );
    }
}

