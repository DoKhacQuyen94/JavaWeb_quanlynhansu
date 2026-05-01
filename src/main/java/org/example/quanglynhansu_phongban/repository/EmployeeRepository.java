package org.example.quanglynhansu_phongban.repository;

import org.example.quanglynhansu_phongban.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
