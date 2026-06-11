package org.lion.restexam.controller;

import lombok.RequiredArgsConstructor;
import org.lion.restexam.dto.MemoDTO;
import org.lion.restexam.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api3/memos")
@RequiredArgsConstructor
public class MemoController3 {
    private final MemoService memoService;

    @GetMapping("/all")
    public ResponseEntity<List<MemoDTO>> getMemos(){
        List<MemoDTO> memoDTOs = memoService.getMemos();
        return ResponseEntity.ok(memoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoDTO> getMemoById(@PathVariable Long id){
        Optional<MemoDTO> memoDTO = memoService.getMemoById(id);
        return memoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MemoDTO> createMemo(@RequestBody MemoDTO memoDTO){
        MemoDTO memoDTO1 = memoService.createMemo(memoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(memoDTO1);
    }

    @PutMapping
    public ResponseEntity<MemoDTO> updateMemo(@RequestBody MemoDTO memoDTO){
        memoService.updateMemo(memoDTO.getId(), memoDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<MemoDTO> deleteMemo(@RequestBody MemoDTO memoDTO){
        memoService.deleteMemo(memoDTO.getId());
        return ResponseEntity.ok().build();
    }




}
