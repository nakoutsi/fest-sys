package com.second.festivalmanagementsystem.repository;

import com.second.festivalmanagementsystem.model.Festival;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CustomFestivalRepositoryImpl implements CustomFestivalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Festival> findFestivalsByFilters(String name, String description, Date startDate, Date endDate, String venue) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Festival> cq = cb.createQuery(Festival.class);
        Root<Festival> festival = cq.from(Festival.class);

        List<Predicate> predicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.equal(festival.get("name"), name));
        }
        if (description != null && !description.isEmpty()) {
            predicates.add(cb.like(cb.lower(festival.get("description")), "%" + description.toLowerCase() + "%"));
        }
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(festival.get("startDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(festival.get("endDate"), endDate));
        }
        if (venue != null && !venue.isEmpty()) {
            predicates.add(cb.equal(festival.get("venue"), venue));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Festival> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
