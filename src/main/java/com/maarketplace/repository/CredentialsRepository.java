package com.maarketplace.repository;

import com.maarketplace.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    public Boolean existsByUsername(String username);

    public Optional<Credentials> findByUsername(String username);
}

