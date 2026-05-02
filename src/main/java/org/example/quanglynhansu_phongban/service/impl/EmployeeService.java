package org.example.quanglynhansu_phongban.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quanglynhansu_phongban.model.Employee;
import org.example.quanglynhansu_phongban.repository.EmployeeRepository;
import org.example.quanglynhansu_phongban.service.IEmployeeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
