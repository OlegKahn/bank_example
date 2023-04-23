package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDTO;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.LicenseService;
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
@RequestMapping("/license")
@AllArgsConstructor
@Tag(name="Лицензия банка", description="Данные о лицензии банка")
public class LicensesController {
    private final LicenseService licenseService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных обо всех лицензиях банка",
            description = "Этот API позволяет получить данные обо всех лицензиях банка"
    )
    @ApiResponse(responseCode = "200", description = "Все данные обо всех лицензиях банка успешно получены")
    @GetMapping()
    public ResponseEntity<List<LicenseDTO>> showAllLicenses() {
        List<LicenseDTO> licenseDTO = licenseService.getAllLicenses().stream()
                .map(license -> modelMapper.map(license, LicenseDTO.class))
                .collect(Collectors.toList());
        return !licenseDTO.isEmpty()? new ResponseEntity<>(licenseDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одной лицензии банка",
            description = "Этот API позволяет получить данные об одной лицензии банка"
    )
    @ApiResponse(responseCode = "200", description = "Данные о лицензии банка успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<LicenseDTO> showLicense(@PathVariable(name = "id") Long id) {
        LicenseDTO licenseDTO = modelMapper.map(licenseService.getLicenseById(id), LicenseDTO.class);
        if(licenseDTO==null){
            throw new EntityNotFoundException("Лицензия с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(licenseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую лицензию банка",
            description = "Этот API позволяет добавить новую лицензию банка")
    @ApiResponse(responseCode = "200", description = "Новая лицензия банка успешно добавлена")
    @PostMapping()
    public ResponseEntity<HttpStatus> createLicense(@RequestBody @Valid LicenseDTO licenseDTO) {
        licenseService.createLicense(modelMapper.map(licenseDTO, License.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующую лицензию банка",
            description = "Этот API позволяет обновить существующую лицензию банка")
    @ApiResponse(responseCode = "200", description = "Лицензия банка успешно обновлена")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateLicense(@RequestBody @Valid LicenseDTO licenseDTO,
                                                @PathVariable("id") Long id) {
        licenseService.updateLicense(modelMapper.map(licenseDTO, License.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить информацию о лицензии банка по ID",
            description = "Этот API позволяет удалить лицензию банка по ID")
    @ApiResponse(responseCode = "200", description = "Лицензия банка успешно удалена")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLicense(@PathVariable("id") Long id) {
        licenseService.deleteLicense(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
