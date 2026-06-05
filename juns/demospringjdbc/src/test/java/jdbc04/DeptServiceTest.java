package jdbc04;

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
@Transactional
@SpringBootTest(classes = Jdbc04App.class)
class DeptServiceTest {

    @Autowired
    private DeptService deptService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        deptService.findAll();
    }

    @Test
    void findById() {
        Dept dept = new Dept();
        dept.setDeptName("test");
        dept.setLocation("test location");

        List<Dept> result = deptService.findByDeptName(dept.getDeptName());
        for (Dept dept1 : result) {
            log.info("dept1={}", dept1.getId());
            Dept temp = deptService.findDeptById(dept1.getId());
            assertEquals(temp.getDeptName(), dept1.getDeptName());
            assertEquals(temp.getLocation(), dept1.getLocation());
        }

    }

    @Test
    void findByDeptName() {
    }

    @Test
    void findByLocation() {
    }

    @Test
    void findByDeptNameAndLocation() {
    }
}