package com.maarketplace.service;

import com.maarketplace.model.Credentials;

import java.time.LocalDateTime;
import java.util.List;

public interface CredentialsService {

    Credentials getCredentials(String username);

    List<Credentials> getCredentialsByCriteria(String role, LocalDateTime registeredFrom, LocalDateTime registeredTo);
}
