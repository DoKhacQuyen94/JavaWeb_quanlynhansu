package org.example.quanglynhansu_phongban.config;

import lombok.RequiredArgsConstructor;
import org.example.quanglynhansu_phongban.model.Department;
import org.example.quanglynhansu_phongban.model.Employee;
import org.example.quanglynhansu_phongban.repository.DepartmentRepository;
import org.example.quanglynhansu_phongban.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final DepartmentRepository departmentRepo;
    private final EmployeeRepository employeeRepo;

    @Override
    public void run(String... args) {
        if (departmentRepo.count() > 0 || employeeRepo.count() > 0) {
            return;
        }

        Department it = new Department();
        it.setName("IT");
        it.setLocation("Hà Nội");

        Department hr = new Department();
        hr.setName("HR");
        hr.setLocation("Đà Nẵng");

        departmentRepo.saveAll(List.of(it, hr));

        Employee e1 = new Employee();
        e1.setName("Quyền");
        e1.setAge(20);
        e1.setStatus(true);
        e1.setAvatar("default.png");
        e1.setDepartment(it);

        Employee e2 = new Employee();
        e2.setName("An");
        e2.setAge(25);
        e2.setStatus(false);
        e2.setAvatar("default.png");
        e2.setDepartment(hr);

        employeeRepo.saveAll(List.of(e1, e2));
    }
}