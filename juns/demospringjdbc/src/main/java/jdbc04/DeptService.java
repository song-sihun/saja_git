package jdbc04;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeptService {

    private final DeptRepository deptRepository;

    public void saveDept(Dept dept) {
        deptRepository.save(dept);
    }
    public List<Dept> findAll() {
        return (List<Dept>) deptRepository.findAll();
    }

    public Dept findDeptById(Integer id) {
        return deptRepository.findDeptById(id).orElse(null);
    }

    public List<Dept> findByDeptName(String deptName) {
        return deptRepository.findByDeptName(deptName);
    }

    public List<Dept> findByLocation(String location) {
        return deptRepository.findByLocation(location);
    }

    public List<Dept> findByDeptNameAndLocation(String deptName, String location) {
        return deptRepository.findByDeptNameAndLocation(deptName, location);
    }
}
