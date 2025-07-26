package com.maarketplace.service.prov;

import com.maarketplace.helpers.validators.TypeValidators;
import com.maarketplace.model.Credentials;
import com.maarketplace.repository.CredentialsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialsService1 {

    @Autowired
    protected CredentialsRepository credentialsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Credentials getCredentials(String username) {
        return this.credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UserCredentialsUsernameNotExistsException(
                        "User Credentials with username '" + username + "' does not exist."
                ));
    }

    public List<Credentials> getCredentialsByCriteria(String role, LocalDateTime registeredFrom, LocalDateTime registeredTo) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Credentials> criteriaQuery = criteriaBuilder.createQuery(Credentials.class);
        Root<Credentials> rootCredentials = criteriaQuery.from(Credentials.class);

        List<Predicate> predicates = new ArrayList<>();

        if (TypeValidators.validateTimestamp(registeredFrom)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootCredentials.get("insertedAt"), registeredFrom));
        }
        if (TypeValidators.validateTimestamp(registeredTo)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(rootCredentials.get("insertedAt"), registeredTo));
        }

        criteriaQuery.select(rootCredentials).where(predicates.toArray(new Predicate[0]));

        return this.entityManager.createQuery(criteriaQuery).getResultList();
    }
}
