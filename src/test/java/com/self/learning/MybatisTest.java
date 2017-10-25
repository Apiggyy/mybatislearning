package com.self.learning;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.self.learning.entity.customer.Department;
import com.self.learning.entity.customer.Employee;
import com.self.learning.mapper.customer.DepartmentMapper;
import com.self.learning.mapper.customer.EmployeeAnnotationMapper;
import com.self.learning.mapper.customer.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;

    @Before
    public void init() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void destory() {
        sqlSession.close();
    }

    @Test
    public void test() {
        //Employee employee = (Employee) sqlSession.selectOne("EmployeeMapper.selectEmployee", 1);
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployee(1);
        System.out.println(employee);
    }

    @Test
    public void testAnnotation() {
        EmployeeAnnotationMapper mapper = sqlSession.getMapper(EmployeeAnnotationMapper.class);
        Employee employee = mapper.selectEmployee(1);
        System.out.println(employee);
    }

    @Test
    public void testGeneratorOfMysqlInsert() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setLastName("xieyonghong");
        employee.setEmail("xieyonghong@qq.com");
        employee.setGender("1");
        mapper.insertEmployee(employee);
        System.out.println(employee);
        sqlSession.commit();
    }

    @Test
    public void testSequenceOfOracleInsert() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setLastName("lisi");
        employee.setEmail("lisi@qq.com");
        mapper.insertEmployee(employee);
        System.out.println(employee);
        sqlSession.commit();
    }

    @Test
    public void testParam() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployeeByParam(1, "weizhiming");
        System.out.println(employee);
    }

    @Test
    public void testMap() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("lastName", "weizhiming");
        Employee employee = mapper.selectEmployeeByMap(map);
        System.out.println(employee);
    }

    //@Test
    //public void testJdbcType() {
    //    EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
    //    Employee employee = new Employee("qianliu",null,null);
    //    mapper.insertEmployee(employee);
    //    System.out.println(employee);
    //    sqlSession.commit();
    //}

    @Test
    public void testReturnList() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employees = mapper.selectEmployeesByLastNameLike("e");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testSelectEmployeesByLastNameLikeReturnMap() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Map<Integer, Employee> map = mapper.selectEmployeesByLastNameLikeReturnMap("e");
        //for(Map.Entry<Integer,Employee> entry : map.entrySet()) {
        //    System.out.println(entry.getKey() + "-" + entry.getValue());
        //}
        System.out.println(map);
    }

    @Test
    public void testSelectEmployeeByResultMap() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployeeByResultMap(1);
        System.out.println(employee);
    }

    @Test
    public void testSelectEmployeeAndDepartmentById() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employee = mapper.selectEmployeeAndDepartmentById(2);
        System.out.println(employee);
    }

/*    @Test
    public void testSelectEmployeeByAssociationStep() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployeeByAssociationStep(4);
        //System.out.println(employee);
        System.out.println(employee.getLastName());
        System.out.println(employee.getDept().getDeptName());
    }*/

    @Test
    public void testGetDepartmentByIdPlus() {
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = departmentMapper.getDepartmentByIdPlus(1);
        System.out.println(department);
    }

/*    @Test
    public void testGetDepartmentByIdStep() {
        DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = departmentMapper.getDepartmentByIdStep(1);
        System.out.println(department.getDeptName());
        System.out.println(department.getEmps().size());
    }*/

    @Test
    public void testSelectEmployeeByDynamicSql() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployeeByDynamicSqlByIf(1, "weizhiming", null);
        System.out.println(employee);
    }

    @Test
    public void testSelectEmployeeByDynamicSqlByChoose() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.selectEmployeeByDynamicSqlByChoose(1, "weizhiming", "360625981@qq.com");
        System.out.println(employee);
    }

    @Test
    public void testUpdateEmployeeByDynamicSql() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setId(1);
        employee.setLastName("Weizm");
        employee.setEmail("henrywzm@163.com");
        mapper.updateEmployeeByDynamicSql(employee);
        sqlSession.commit();
        System.out.println(employee);
    }

/*    @Test
    public void testInsertEmpsBatch() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee1 = new Employee("emp-05","emp05@qq.com","1",new Department(30,null));
        Employee employee2 = new Employee("emp-06","emp06@qq.com","2",new Department(30,null));
        List<Employee> emps = new ArrayList<Employee>();
        emps.add(employee1);
        emps.add(employee2);
        mapper.insertEmpsBatch(emps);
        sqlSession.commit();
    }*/

    @Test
    public void testInnerParameter() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee();
        employee.setLastName("e");
        List<Employee> employees = mapper.testInnerParameter(employee);
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    @Test
    public void testFirstLevelCache() {
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee1 = mapper.selectEmployee(1);
        System.out.println(employee1);
        Employee employee2 = mapper.selectEmployee(1);
        System.out.println(employee2);
    }

    @Test
    public void testSecondaryLevelCache() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
        List<Employee> emp1 = mapper1.selectEmployeeAndDepartmentById(1);
        System.out.println(emp1);
        sqlSession1.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
        List<Employee> emp2 = mapper2.selectEmployeeAndDepartmentById(1);
        System.out.println(emp2);
        sqlSession2.close();
    }

    @Test
    public void testPageHelper() {
        final EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        PageInfo<Employee> pageInfo = PageHelper.startPage(4, 5).doSelectPageInfo(new ISelect() {
            public void doSelect() {
                mapper.selectAllEmployees();
            }
        });
        System.out.println("总页数:" + pageInfo.getPages());
        System.out.println("当前页:" + pageInfo.getPageNum());
        System.out.println("页大小:" + pageInfo.getPageSize());
        System.out.println("总条数:" + pageInfo.getTotal());
        for (Employee Employee : pageInfo.getList()) {
            System.out.println(Employee);
        }
    }

    @Test
    public void testMbgFromJava() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File(System.getProperty("user.dir") + "/src/main/resources/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

}
