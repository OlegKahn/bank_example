package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDTO;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.AuditService;
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
@RequestMapping("/audit")
@AllArgsConstructor
@Tag(name="Аудит", description="Данные об аудите")
public class AuditController {
    private final AuditService auditService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных об аудите",
            description = "Этот API позволяет получить данные об аудите"
    )
    @ApiResponse(responseCode = "200", description = "Все данные оь аудите успешно получены")
    @GetMapping()
    public ResponseEntity<List<AuditDTO>> showAllAudit() {
        List<AuditDTO> auditDTO = auditService.getAllAudit().stream()
                .map(audit -> modelMapper.map(audit, AuditDTO.class))
                .collect(Collectors.toList());
        return !auditDTO.isEmpty()? new ResponseEntity<>(auditDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одном аудите",
            description = "Этот API позволяет получить данные об одном аудите"
    )
    @ApiResponse(responseCode = "200", description = "Данные об аудите успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<AuditDTO> showAudit(@PathVariable(name = "id") Long id) {
        AuditDTO auditDTO = modelMapper.map(auditService.getAuditById(id), AuditDTO.class);
        if(auditDTO==null){
            throw new EntityNotFoundException("Audit с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(auditDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый аудит",
            description = "Этот API позволяет добавить новый аудит")
    @ApiResponse(responseCode = "200", description = "Новый аудит успешно добавлен")
    @PostMapping()
    public ResponseEntity<HttpStatus> createAudit(@RequestBody @Valid AuditDTO auditDTO) {
        auditService.createAudit(modelMapper.map(auditDTO, Audit.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующий аудит",
            description = "Этот API позволяет обновить существующий аудит")
    @ApiResponse(responseCode = "200", description = "Информация об аудите успешно обновлена")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAudit(@RequestBody @Valid AuditDTO auditDTO,
                                            @PathVariable("id") Long id) {
        auditService.updateAudit(modelMapper.map(auditDTO, Audit.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить аудит по ID",
            description = "Этот API позволяет удалить существующий аудит по ID")
    @ApiResponse(responseCode = "200", description = "Информация об аудите успешно удалена")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAudit(@PathVariable("id") Long id) {
        auditService.deleteAudit(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
