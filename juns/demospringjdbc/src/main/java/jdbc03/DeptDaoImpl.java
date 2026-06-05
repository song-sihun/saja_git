package jdbc03;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Dept> rowMapper = new BeanPropertyRowMapper<>(Dept.class);

    @Override
    public int insertDept(Dept dept) {
        String sql = "insert into dept(dname,loc) values(?,?)";
        return jdbcTemplate.update(sql, dept.getName(), dept.getLocation());
    }

    @Override
    public int updateDept(int id, Dept dept) {
        String sql = "update dept set dname=?,loc=? where deptno=?";
        return jdbcTemplate.update(sql, dept.getName(), dept.getLocation(), id);
    }

    @Override
    public int deleteDept(int id) {
        String sql = "delete from dept where deptno=?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Dept> selectDeptById(int id) {
        String sql = """
            SELECT 
                deptno AS id,
                dname AS name,
                loc AS location
            FROM dept
            WHERE deptno = ?
        """;
        List<Dept> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public List<Dept> selectDeptByName(String name) {
        String  sql = """
            SELECT
                deptno AS id,
                dname AS name,
                loc AS location
            FROM dept
            WHERE dname LIKE ?
        """;
        return jdbcTemplate.query(sql, rowMapper, name);
    }

    @Override
    public List<Dept> selectAllDept() {
        String sql = """
            SELECT
                deptno AS id,
                dname AS name,
                loc AS location
            FROM dept
        """;

        return jdbcTemplate.query(sql, rowMapper);
    }
}
