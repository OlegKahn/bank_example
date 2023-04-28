package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDTO;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.CertificateService;
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
@RequestMapping("/certificate")
@AllArgsConstructor
@Tag(name="Сертификат банка", description="Данные о сертификате банка")
public class CertificatesController {
    private final CertificateService certificateService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных обо всех сертификатах банка",
            description = "Этот API позволяет получить данные обо всех сертификатах банка"
    )
    @ApiResponse(responseCode = "200", description = "Все данные обо всех сертификатах банка успешно получены")
    @GetMapping()
    public ResponseEntity<List<CertificateDTO>> showAllCertificates() {
        List<CertificateDTO> certificateDTO = certificateService.getAllCertificates().stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDTO.class))
                .collect(Collectors.toList());
        return !certificateDTO.isEmpty()? new ResponseEntity<>(certificateDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одном сертификате банка",
            description = "Этот API позволяет получить данные об одном сертификате банка"
    )
    @ApiResponse(responseCode = "200", description = "Данные о сертификате банка успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<CertificateDTO> showCertificate(@PathVariable(name = "id") Long id) {
        CertificateDTO certificateDTO = modelMapper.map(certificateService.getCertificatedById(id), CertificateDTO.class);
        if(certificateDTO==null){
            throw new EntityNotFoundException("Сертификат с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(certificateDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый сертификат банка",
            description = "Этот API позволяет добавить новый сертификат банка")
    @ApiResponse(responseCode = "200", description = "Новый сертификат банка успешно добавлен")
    @PostMapping()
    public ResponseEntity<HttpStatus> createCertificate(@RequestBody @Valid CertificateDTO certificateDTO) {
        certificateService.createCertificate(modelMapper.map(certificateDTO, Certificate.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующий сертификат банка",
            description = "Этот API позволяет обновить существующий сертификат банка")
    @ApiResponse(responseCode = "200", description = "Сертификат банка успешно обновлен")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCertificate(@RequestBody @Valid CertificateDTO certificateDTO,
                                                @PathVariable("id") Long id) {
        certificateService.updateCertificate(modelMapper.map(certificateDTO, Certificate.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить информацию о сертификате банка по ID",
            description = "Этот API позволяет удалить сертификат банка по ID")
    @ApiResponse(responseCode = "200", description = "Сертификат банка успешно удален")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCertificate(@PathVariable("id") Long id) {
        certificateService.deleteCertificate(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
