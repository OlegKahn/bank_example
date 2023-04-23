package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.BranchService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/branch")
@AllArgsConstructor
@Tag(name="Отделение банка", description="Данные об отделении банка")
public class BranchesController {
    private final BranchService branchService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных обо всех отделениях банка",
            description = "Этот API позволяет получить данные обо всех отделениях банка"
    )
    @ApiResponse(responseCode = "200", description = "Все данные обо всех отделениях банка успешно получены")
    @GetMapping()
    public ResponseEntity<List<BranchDTO>> showAllBranches() {
        List<BranchDTO> branchDTO = branchService.getAllBranches().stream()
                .map(branch -> modelMapper.map(branch, BranchDTO.class))
                .collect(Collectors.toList());
        return !branchDTO.isEmpty()? new ResponseEntity<>(branchDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одном отделении банке",
            description = "Этот API позволяет получить данные об одном отделении банке"
    )
    @ApiResponse(responseCode = "200", description = "Данные об отделении банка успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> showBranch(@PathVariable(name = "id") Long id) {
        BranchDTO branchDTO = modelMapper.map(branchService.getBranchById(id), BranchDTO.class);
        if(branchDTO==null){
            throw new EntityNotFoundException("Отделение с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(branchDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новое отделение банка",
            description = "Этот API позволяет добавить новое отделение банка")
    @ApiResponse(responseCode = "200", description = "Новое отделение банка успешно добавлено")
    @PostMapping()
    public ResponseEntity<HttpStatus> createBranch(@RequestBody @Valid BranchDTO branchDTO) {
        branchService.createBranch(modelMapper.map(branchDTO, Branch.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующее отделение банка",
            description = "Этот API позволяет обновить существующее отделение банка")
    @ApiResponse(responseCode = "200", description = "Отделение банка успешно обновлено")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBranch(@RequestBody @Valid BranchDTO branchDTO,
                                                @PathVariable("id") Long id) {
        branchService.updateBranch(modelMapper.map(branchDTO, Branch.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить информацию об отделении банка по ID",
            description = "Этот API позволяет удалить существующее отделение банка по ID")
    @ApiResponse(responseCode = "200", description = "Отделение банка успешно удалено")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBranch(@PathVariable("id") Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
