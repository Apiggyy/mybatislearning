package com.self.learning.mapper;

import com.self.learning.entity.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeAnnotationMapper {
    @Select("select * from tbl_employee where id = #{id}")
    Employee selectEmployee(Integer id);
}
