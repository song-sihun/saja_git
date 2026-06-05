package jdbc04;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends CrudRepository<Dept, Long> {
    List<Dept> findByDeptName(String deptName);
    List<Dept> findByDeptNameAndLocation(String deptName, String location);
    List<Dept> findByLocation(String location); //쿼리 매서드 쿼리로 바뀐다.
    List<Dept> findByDeptNameContaining(String deptName);
    Dept findDeptById(Integer id);
}
