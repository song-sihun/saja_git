package jdbc03;

import jdbcExam02.JdbcExam02;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = Jdbc03App.class)
@Transactional
class DeptServiceTest {

    @Autowired
    DeptService deptService;

    @BeforeEach
    void setUp() {
        log.info("테스트 시작");
    }

    @AfterEach
    void tearDown() {
        log.info("테스트 종료");
    }

    @Test
    void selectDeptByName() {
        Dept dept1 = new Dept();
        dept1.setName("Test Dept");
        dept1.setLocation("Test Loc");
        deptService.insertDept(dept1);

        Dept dept2 = new Dept();
        dept2.setName("Test Dept");
        dept2.setLocation("Test Loc");
        deptService.insertDept(dept2);

        List<Dept> findDeptList = deptService.selectDeptByName("Test");
        for(Dept findDept : findDeptList){
            log.info(findDept.getName());
            Dept temp = deptService.selectDeptById(findDept.getId());
            log.info(temp.getName());

        }
    }

    @Test
    void selectAllDept() {
        Dept dept1 = new Dept();
        dept1.setName("Test Dept");
        dept1.setLocation("Test Loc");
        deptService.insertDept(dept1);

        Dept dept2 = new Dept();
        dept2.setName("Test Dept");
        dept2.setLocation("Test Loc");
        deptService.insertDept(dept2);


        List<Dept> deptList = deptService.selectAllDept();
        log.info(deptList.toString());
        log.info("모든 부서 조회 성공");
    }

    @Test
    void insertDept() {
        Dept dept = new Dept();
        dept.setName("Test Dept1");
        dept.setLocation("Test loc");
        boolean result = deptService.insertDept(dept);
        assertTrue(result);
        log.info("부서 생성 성공");
    }

    @Test
    void updateDept() {
        Dept dept = new Dept();
        dept.setName("Before");
        dept.setLocation("Seoul");
        deptService.insertDept(dept);

        List<Dept> targetDept = deptService.selectDeptByName("Before");

        dept.setName("After");
        dept.setLocation("Busan");

        for (Dept findDept : targetDept) {
            boolean result = deptService.updateDept(findDept.getId(), dept);
            assertTrue(result);
        }
    }

    @Test
    void selectDeptById() {
        Dept dept = new Dept();
         dept.setName("Test Dept");
        dept.setLocation("Test Loc");

        deptService.insertDept(dept);
        List<Dept> targetDept = deptService.selectDeptByName(dept.getName());
        Dept findDept = targetDept.getFirst();
        Dept temp = deptService.selectDeptById(findDept.getId());
        assertEquals("Test Dept", temp.getName());
        assertEquals("Test Loc", temp.getLocation());
    }

    @Test
    void deleteDeptById() {
        Dept dept = new Dept();
        dept.setName("Test Dept");
        dept.setLocation("Test Loc");
        deptService.insertDept(dept);

        List<Dept> targetDept = deptService.selectDeptByName(dept.getName());
        Dept findDept = targetDept.getFirst();
        Dept temp = deptService.selectDeptById(findDept.getId());
        boolean result = deptService.deleteDeptById(temp.getId());
        assertTrue(result);
    }
}