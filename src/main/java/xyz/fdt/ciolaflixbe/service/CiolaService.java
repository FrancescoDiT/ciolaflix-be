package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.model.AuthAccount;
import it.trinex.blackout.repository.AuthAccountRepo;
import it.trinex.blackout.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.fdt.ciolaflixbe.dto.request.SignupRequestDTO;
import xyz.fdt.ciolaflixbe.model.CiolaMan;
import xyz.fdt.ciolaflixbe.repo.CiolaRepo;
import xyz.fdt.ciolaflixbe.dto.request.UpdateUserInfoRequestDTO;
import xyz.fdt.ciolaflixbe.exception.user.SignupException;
import xyz.fdt.ciolaflixbe.exception.user.DuplicateEmailException;
import xyz.fdt.ciolaflixbe.exception.user.UserNotFoundException;
import xyz.fdt.ciolaflixbe.dto.response.UserInfoDTO;
import it.trinex.blackout.service.CurrentUserService;
import it.trinex.blackout.security.BlackoutUserPrincipal;

@Service
@RequiredArgsConstructor
public class CiolaService {
    private final AuthService authService;
    private final AuthAccountRepo authAccountRepo;
    private final PasswordEncoder passwordEncoder;
    private final CiolaRepo ciolaRepo; // Your business repository
    private final CurrentUserService<BlackoutUserPrincipal> currentUserService;

    public void signup(SignupRequestDTO request) {
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

    public UserInfoDTO getUserInfo() {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        CiolaMan ciolaMan = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        AuthAccount authAccount = authAccountRepo.findById(ciolaMan.getAuthAccountId())
                .orElseThrow(() -> new UserNotFoundException("Auth account not found for user id: " + currentUserId));

        return UserInfoDTO.builder()
                .name(authAccount.getFirstName())
                .lastname(authAccount.getLastName())
                .build();
    }

    public void updateUserInfo(UpdateUserInfoRequestDTO request) {
        Long currentUserId = currentUserService.getCurrentPrincipal().getUserId();
        CiolaMan ciolaMan = ciolaRepo.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        AuthAccount authAccount = authAccountRepo.findById(ciolaMan.getAuthAccountId())
                .orElseThrow(() -> new UserNotFoundException("Auth account not found for user id: " + currentUserId));

        authAccount.setFirstName(request.getName());
        authAccount.setLastName(request.getLastname());

        authAccountRepo.save(authAccount);
    }
}
