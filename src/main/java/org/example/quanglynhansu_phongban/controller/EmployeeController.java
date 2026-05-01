package org.example.quanglynhansu_phongban.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.quanglynhansu_phongban.model.Department;
import org.example.quanglynhansu_phongban.model.Employee;
import org.example.quanglynhansu_phongban.repository.DepartmentRepository;
import org.example.quanglynhansu_phongban.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepository;
    private final Cloudinary cloudinary;

    @GetMapping
    public String list(Model model) {
        List<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "list";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "form";
    }

    @PostMapping("/add")
    public String save(
            @ModelAttribute Employee employee,
            @RequestParam("file") MultipartFile file
    ) {

        try {
            if (!file.isEmpty()) {
                String publicId = UUID.randomUUID().toString();
                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "public_id", publicId
                        )
                );
                String url = uploadResult.get("secure_url").toString();
                employee.setAvatar(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        employeeRepo.save(employee);
        return "redirect:/";
    }
}
