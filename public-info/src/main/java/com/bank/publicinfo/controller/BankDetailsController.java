package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.BankDetailsService;
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
@RequestMapping("/bankdetails")
@AllArgsConstructor
@Tag(name="Детали банка", description="Данные о деталях банка")
public class BankDetailsController {
    private final BankDetailsService bankDetailsService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных обо всех деталях банка",
            description = "Этот API позволяет получить данные обо всех деталях банк"
    )
    @ApiResponse(responseCode = "200", description = "Все данные о деталях банка успешно получены")
    @GetMapping()
    public ResponseEntity<List<BankDetailsDTO>> showAllBankDetails() {
        List<BankDetailsDTO> bankDetailsDTO = bankDetailsService.getAllBankDetails().stream()
                .map(bd -> modelMapper.map(bd, BankDetailsDTO.class))
                .collect(Collectors.toList());
        return !bankDetailsDTO.isEmpty()? new ResponseEntity<>(bankDetailsDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одном банке",
            description = "Этот API позволяет получить данные об одном банке"
    )
    @ApiResponse(responseCode = "200", description = "Данные о деталях одного банка успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<BankDetailsDTO> showBankDetails(@PathVariable(name = "id") Long id) {
        BankDetailsDTO bankDetailsDTO = modelMapper.map(bankDetailsService.getBankDetailsById(id), BankDetailsDTO.class);
        if(bankDetailsDTO==null){
            throw new EntityNotFoundException("Банк с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(bankDetailsDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить информацию о банку",
            description = "Этот API позволяет добавить детали банка")
    @ApiResponse(responseCode = "200", description = "Информация о банке успешно добавлена")
    @PostMapping()
    public ResponseEntity<HttpStatus> createBankDetails(@RequestBody @Valid BankDetailsDTO bankDetailsDTO) {
        bankDetailsService.createBankDetails(modelMapper.map(bankDetailsDTO, BankDetails.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующую информацию о банке",
            description = "Этот API позволяет обновить существующие детали банка")
    @ApiResponse(responseCode = "200", description = "Информация о банке успешно обновлена")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBankDetails(@RequestBody @Valid BankDetailsDTO bankDetailsDTO,
                                                @PathVariable("id") Long id) {
        bankDetailsService.updateBankDetails(modelMapper.map(bankDetailsDTO, BankDetails.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить информацию о банке по ID",
            description = "Этот API позволяет удалить существующую информацию о банке по ID")
    @ApiResponse(responseCode = "200", description = "Информация о банке успешно удалена")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBankDetails(@PathVariable("id") Long id) {
        bankDetailsService.deleteBankDetails(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
