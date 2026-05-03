package org.example.quanglynhansu_phongban.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.quanglynhansu_phongban.model.Department;
import org.example.quanglynhansu_phongban.model.Employee;
import org.example.quanglynhansu_phongban.repository.DepartmentRepository;
import org.example.quanglynhansu_phongban.service.IEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final IEmployeeService employeeService;
    private final DepartmentRepository departmentRepository;
    private final Cloudinary cloudinary;

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) String departmentId,
            @RequestParam(required = false) String ageFrom,
            @RequestParam(required = false) String ageTo
    ) {
        Integer from = (ageFrom == null || ageFrom.isBlank() || ageFrom.equals("null"))
                ? null : Integer.valueOf(ageFrom);

        Integer to = (ageTo == null || ageTo.isBlank() || ageTo.equals("null"))
                ? null : Integer.valueOf(ageTo);

        Long depId = (departmentId == null || departmentId.isBlank() || departmentId.equals("null"))
                ? null : Long.valueOf(departmentId);

        Sort sort = direction.equals("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employeePage = employeeService.searchAdvanced(
                keyword,
                depId,
                from,
                to,
                pageable
        );

        model.addAttribute("employees", employeePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());

        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("ageFrom", ageFrom);
        model.addAttribute("ageTo", ageTo);

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
        employeeService.save(employee);
        return "redirect:/";
    }
}
