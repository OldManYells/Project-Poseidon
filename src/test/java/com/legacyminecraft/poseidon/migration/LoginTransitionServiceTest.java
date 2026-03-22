package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginTransitionService;
import org.junit.Assert;
import org.junit.Test;

public class LoginTransitionServiceTest {
    @Test
    public void completionResultMarksLoginAsComplete() {
        LoginTransitionService.CompletionResult completionResult =
                LoginTransitionService.CompletionResult.completed();

        Assert.assertTrue(completionResult.shouldMarkLoginComplete());
    }
}
