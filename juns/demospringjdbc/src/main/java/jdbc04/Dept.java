package jdbc04;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("dept")
public class Dept {
    @Id
    @Column(value = "deptno")
    private Integer id;

    @Column(value = "dname")
    private String deptName;

    @Column(value = "loc")
    private String location;

    public Dept orElse(Object o) {
        return this;
    }
}
