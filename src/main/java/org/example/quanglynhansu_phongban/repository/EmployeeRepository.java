package org.example.quanglynhansu_phongban.repository;

import org.example.quanglynhansu_phongban.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Employee> findAll(Pageable pageable);
}
