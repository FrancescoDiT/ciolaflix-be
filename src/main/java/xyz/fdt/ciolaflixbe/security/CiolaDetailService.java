package xyz.fdt.ciolaflixbe.security;

import it.trinex.blackout.exception.AccountNotActiveException;
import it.trinex.blackout.model.AuthAccount;
import it.trinex.blackout.repository.AuthAccountRepo;
import it.trinex.blackout.security.BlackoutUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiolaDetailService implements UserDetailsService {

    private final CiolaRepo userRepo;
    private final AuthAccountRepo authAccountRepo;

    @Override
    public BlackoutUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Load AuthAccount from auth database
        AuthAccount authAccount = authAccountRepo.findByUsername(username).orElse(
            authAccountRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
        );

        // 2. Check if account is active
        if (!authAccount.isActive()) {
            throw new AccountNotActiveException("Account not active: " + username);
        }

        // 3. Load your business entity from primary database
        CiolaMan user = userRepo.findByAuthAccountId(authAccount.getId())
            .orElseThrow(() -> new UsernameNotFoundException(username));

        // 4. Build authorities
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // 5. Return your custom principal with all fields
        return BlackoutUserPrincipal.builder()
            // Default Blackout fields
            .authId(authAccount.getId())
            .userId(user.getId())
            .authorities(authorities)
            .username(username)
            .password(authAccount.getPasswordHash())
            .build();
    }
}
