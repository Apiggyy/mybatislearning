package com.self.learning.mapper.customer;

import com.self.learning.entity.customer.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeAnnotationMapper {
    @Select("select * from tbl_employee where id = #{id}")
    Employee selectEmployee(Integer id);
}
