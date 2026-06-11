package org.lion.restexam.dto;

import lombok.Getter;
import lombok.Setter;
import org.lion.restexam.domain.Memo;

@Getter
@Setter
public class MemoDTO {
    private Long id;
    private String description;

    public static MemoDTO fromEntity(Memo memoEntity) {
        MemoDTO dto = new MemoDTO();
        dto.setId(memoEntity.getId());
        dto.setDescription(memoEntity.getDescription());
        return dto;
    }
}
