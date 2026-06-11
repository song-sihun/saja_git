package org.lion.restexam.service;

import lombok.RequiredArgsConstructor;
import org.lion.restexam.domain.Memo;
import org.lion.restexam.dto.MemoDTO;
import org.lion.restexam.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    public List<MemoDTO> getMemos() {
        List<Memo> memos = memoRepository.findAll();
        return memos.stream()
                .map(MemoDTO::fromEntity)
                .toList();    // 결과를 다시 List로 변환
    }

    public Optional<MemoDTO> getMemoById(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow();
        MemoDTO memoDTO = MemoDTO.fromEntity(memo);
        return Optional.of(memoDTO);
    }

    @Transactional
    public MemoDTO createMemo(MemoDTO  memoDTO) {
        Memo memo = new Memo();
        memo.setDescription(memoDTO.getDescription());
        memoRepository.save(memo);

        MemoDTO memoDTO1 = new MemoDTO();
        memoDTO1.setId(memo.getId());
        memoDTO1.setDescription(memoDTO.getDescription());
        return memoDTO1;
    }

    @Transactional
    public MemoDTO updateMemo(Long id, MemoDTO memoDTO) {
        Memo memoEntity = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found"));
        memoEntity.setDescription(memoDTO.getDescription());
        return MemoDTO.fromEntity(memoEntity);
    }

    @Transactional
    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }

}
