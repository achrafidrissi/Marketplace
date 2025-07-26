package com.maarketplace.service;

import com.maarketplace.exception.NotFoundException;
import com.maarketplace.helpers.validators.TypeValidators;
import com.maarketplace.model.Credentials;
import com.maarketplace.repository.CredentialsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialsServiceImpl implements CredentialsService{

    private final CredentialsRepository credentialsRepository;
    private final EntityManager entityManager;

    public CredentialsServiceImpl(CredentialsRepository credentialsRepository,
                                  EntityManager entityManager) {
        this.credentialsRepository = credentialsRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Credentials getCredentials(String username) {
        return credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        "User Credentials with username '" + username + "' does not exist."
                ));
    }

    @Override
    public List<Credentials> getCredentialsByCriteria(String role, LocalDateTime registeredFrom, LocalDateTime registeredTo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Credentials> query = cb.createQuery(Credentials.class);
        Root<Credentials> root = query.from(Credentials.class);

        List<Predicate> predicates = new ArrayList<>();

        if (TypeValidators.validateTimestamp(registeredFrom)) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("insertedAt"), registeredFrom));
        }

        if (TypeValidators.validateTimestamp(registeredTo)) {
            predicates.add(cb.lessThanOrEqualTo(root.get("insertedAt"), registeredTo));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
