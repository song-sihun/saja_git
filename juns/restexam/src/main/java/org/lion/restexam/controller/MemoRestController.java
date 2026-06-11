package org.lion.restexam.controller;

import lombok.RequiredArgsConstructor;
import org.lion.restexam.domain.Memo;
import org.lion.restexam.dto.MemoDTO;
import org.lion.restexam.dto.ProductDTO;
import org.lion.restexam.repository.MemoRepository;
import org.lion.restexam.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemoRestController {

    private final MemoService memoService;

    @GetMapping("/memos")
    public List<MemoDTO> getMemos(){
        return memoService.getMemos();
    }

    @GetMapping("/memos/{id}")
    public Optional<MemoDTO> getMemo(@PathVariable Long id){
        return memoService.getMemoById(id);
    }

    @PostMapping("/memos/add")
    public MemoDTO createMemo(@RequestBody MemoDTO memoDTO){
        return memoService.createMemo(memoDTO);
    }

    @PutMapping("/memos/{id}")
    public MemoDTO updateMemo(@PathVariable Long id, @RequestBody MemoDTO memoDTO){
        return memoService.updateMemo(id, memoDTO);
    }

    @DeleteMapping("/memos/delete/{id}")
    public void deleteMemo(@PathVariable Long id){
        memoService.deleteMemo(id);
    }


}
