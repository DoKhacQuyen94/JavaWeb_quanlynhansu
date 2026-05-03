package org.example.quanglynhansu_phongban.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.quanglynhansu_phongban.model.Employee;
import org.example.quanglynhansu_phongban.repository.EmployeeRepository;
import org.example.quanglynhansu_phongban.service.IEmployeeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepo;

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return  employeeRepo.findAll(pageable);
    }

    @Override
    public void save(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return employeeRepo.findByNameContainingIgnoreCase(name, pageable);
    }
    @Override
    public Page<Employee> searchAdvanced(
            String keyword,
            Long departmentId,
            Integer ageFrom,
            Integer ageTo,
            Pageable pageable
    ) {

        return employeeRepo.findAll((root, query, cb) -> {

            List<Predicate> predicates = new java.util.ArrayList<>();

            // ===== 1. SEARCH NAME =====
            if (keyword != null && !keyword.isEmpty()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
            }

            // ===== 2. DEPARTMENT =====
            if (departmentId != null) {
                predicates.add(
                        cb.equal(root.get("department").get("id"), departmentId)
                );
            }

            // ===== 3. AGE FROM =====
            if (ageFrom != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("age"), ageFrom)
                );
            }

            // ===== 4. AGE TO =====
            if (ageTo != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("age"), ageTo)
                );
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));

        }, pageable);
    }

}
