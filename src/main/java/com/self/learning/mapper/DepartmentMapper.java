package com.self.learning.mapper;

import com.self.learning.entity.Department;

public interface DepartmentMapper {
    Department getDepartmentById(Integer id);

    Department getDepartmentByIdPlus(Integer id);

    Department getDepartmentByIdStep(Integer id);
}
