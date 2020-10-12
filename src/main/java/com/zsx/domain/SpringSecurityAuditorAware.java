package com.zsx.domain;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        String userId = SecurityUtils.getCurrentUserId();
        String userId = "abc123";
        if (userId != null){
            return Optional.of(userId);
        } else {
            return Optional.empty();
        }
    }
}