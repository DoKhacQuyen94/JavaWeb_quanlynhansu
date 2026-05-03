package org.example.quanglynhansu_phongban.spec;
import org.example.quanglynhansu_phongban.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filter(
            String keyword,
            Long departmentId,
            Integer minAge,
            Integer maxAge
    ) {

        return (root, query, cb) -> {

            List<Predicate> p = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                p.add(cb.like(root.get("name"), "%" + keyword + "%"));
            }

            if (departmentId != null) {
                p.add(cb.equal(root.get("department").get("id"), departmentId));
            }

            if (minAge != null) {
                p.add(cb.greaterThanOrEqualTo(root.get("age"), minAge));
            }

            if (maxAge != null) {
                p.add(cb.lessThanOrEqualTo(root.get("age"), maxAge));
            }

            return cb.and(p.toArray(new Predicate[0]));
        };
    }
}