package org.example.quanglynhansu_phongban.service;

import org.example.quanglynhansu_phongban.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<Employee> findAll(Pageable pageable);
    void save(Employee employee);
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Employee> searchAdvanced(
            String keyword,
            Long departmentId,
            Integer ageFrom,
            Integer ageTo,
            Pageable pageable
    );
}
