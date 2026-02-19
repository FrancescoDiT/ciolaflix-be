package xyz.fdt.ciolaflixbe.config;

import it.trinex.blackout.repository.AuthAccountRepo;
import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.CurrentUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlackoutConfig {
    @Bean
    public CurrentUserService<BlackoutUserPrincipal> currentUserService(AuthAccountRepo authAccountRepo) {
        return new CurrentUserService<>(authAccountRepo);
    }
}
