package jdbc03;

import java.util.List;
import java.util.Optional;

public interface DeptDao {
    int insertDept(Dept dept);
    int updateDept(int id, Dept dept);
    int deleteDept(int id);
    Optional<Dept> selectDeptById(int id);
    List<Dept> selectDeptByName(String name);
    List<Dept> selectAllDept();

}
