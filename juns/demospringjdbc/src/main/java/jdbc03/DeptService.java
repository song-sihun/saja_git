package jdbc03;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptService {

    private final DeptDao deptDao;

    public Dept selectDeptById(int id) {
        Optional<Dept> result = deptDao.selectDeptById(id);
        return deptDao.selectDeptById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DEPT));
    }

    public List<Dept> selectDeptByName(String name) {
        return deptDao.selectDeptByName(name);
    }

    public List<Dept> selectAllDept() {
        return deptDao.selectAllDept();
    }

    public boolean insertDept(Dept dept) {
        if (deptDao.insertDept(dept) < 1) {
            throw new CustomException(ErrorCode.DEPT_INSERT_FAILED);
        }
        return true;
    }

    public boolean updateDept(int id, Dept dept) {
        if (id <= 0) {
            throw new CustomException(ErrorCode.INVALID_DEPT_ID);
        }

        int result = deptDao.updateDept(id, dept);

        if (deptDao.updateDept(id, dept) < 1) {
            throw new CustomException(ErrorCode.DEPT_UPDATE_FAILED);
        }
        return true;
    }

    public boolean deleteDeptById(int id) {
        if (deptDao.deleteDept(id) < 1) {
            throw new CustomException(ErrorCode.DEPT_DELETE_FAILED);
        }
        return true;
    }
}
