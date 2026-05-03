package org.example.quanglynhansu_phongban.dto;

import lombok.Data;

@Data
public class EmployeeFilter {
    private String keyword;
    private Long departmentId;
    private Integer minAge;
    private Integer maxAge;
}