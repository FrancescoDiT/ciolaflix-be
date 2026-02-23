package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.model.AuthAccount;
import it.trinex.blackout.repository.AuthAccountRepo;
import it.trinex.blackout.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.fdt.ciolaflixbe.dto.request.SignupRequest;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.exception.user.SignupException;
import xyz.fdt.ciolaflixbe.exception.user.DuplicateEmailException;

@Service
@RequiredArgsConstructor
public class CiolaService {
    private final AuthService authService;
    private final AuthAccountRepo authAccountRepo;
    private final PasswordEncoder passwordEncoder;
    private final CiolaRepo ciolaRepo; // Your business repository

    public void signup(SignupRequest request) {
        AuthAccount authAccount = null;

        try {
            // 1. Create AuthAccount in auth database
            authAccount = authService.registerAuthAccount(
                AuthAccount.builder()
                    .firstName(request.getName())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .passwordHash(passwordEncoder.encode(request.getPassword()))
                    .isActive(true)
                    .build()
            );

            // 2. Create business entity in primary database
            // Note is not necessary to save email, password, firstName and lastName in the user entity since
            // those fields are already included in the linked AuthAccount entity
            CiolaMan ciolaMan = CiolaMan.builder()
                .authAccountId(authAccount.getId())
                .build();

            ciolaRepo.save(ciolaMan);

        } catch (IllegalArgumentException e) {
            if (authAccount != null && authAccount.getId() != null) {
                authAccountRepo.deleteById(authAccount.getId());
            }
            throw new DuplicateEmailException(e.getMessage(), e);
        } catch (Exception e) {
            if (authAccount != null && authAccount.getId() != null) {
                authAccountRepo.deleteById(authAccount.getId());
            }
            throw new SignupException(e.getMessage(), e);
        }
    }
}
