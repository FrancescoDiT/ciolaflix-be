package xyz.fdt.ciolaflixbe.service;

import it.trinex.blackout.exception.UnauthorizedException;
import it.trinex.blackout.security.BlackoutUserPrincipal;
import it.trinex.blackout.service.BlackoutUserDetailService;
import it.trinex.blackout.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import xyz.fdt.ciolaflixbe.dto.request.EventRequest;
import xyz.fdt.ciolaflixbe.model.Session;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyService {

    private final CurrentUserService<BlackoutUserPrincipal>  currentUserService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private Map<Long, Session> currentSessions;


    public String createSession(){
        Long userId = currentUserService.getCurrentPrincipal().getUserId();
        // Remove existing session if present
        currentSessions.remove(userId);

        String sessionId = generateSessionId();
        Session session = new Session(sessionId, Instant.now());
        currentSessions.put(userId, session);

        return sessionId;
    }

    public void sendEvent(EventRequest request){
        Long userId = currentUserService.getCurrentPrincipal().getUserId();

        if(currentSessions.get(userId) == null){
            throw new UnauthorizedException("You don't have permission to send on this session");
        }

        if(!currentSessions.get(userId).equals(request.getSessionId())){
            throw new UnauthorizedException("You don't have permission to send on this session");
        }

        simpMessagingTemplate.convertAndSend("/topic/party/"+request.getSessionId(), request);
        log.info("Sent event {} to {}", request.getEvent().toString(), request.getSessionId());
    }

    private String generateSessionId(){
        return "ciola-" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 9);
    }

}
