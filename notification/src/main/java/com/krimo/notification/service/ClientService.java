package com.karan.notification.service;

import com.karan.notification.client.UserProfileClient;
import com.karan.notification.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface ClientService {
    String getEmail(Long id);
}

@RequiredArgsConstructor
@Service
class UserProfileClientService implements ClientService {

    private final UserProfileClient userProfileClient;

    @Override
    public String getEmail(Long id){
        ResponseEntity<String> response = userProfileClient.getUserEmail(id);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApiRequestException("Failed to get user email");
        }

        return response.getBody();
    }
}
