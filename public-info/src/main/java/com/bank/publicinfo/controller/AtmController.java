package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDTO;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.service.interfacePublicInfo.AtmService;
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
@RequestMapping("/atm")
@AllArgsConstructor
@Tag(name="Банкомат", description="Данные о банкомате")
public class AtmController {
    private final AtmService atmService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Получение данных о банкоматах",
            description = "Этот API позволяет получить данные обо всех банкоматах"
    )
    @ApiResponse(responseCode = "200", description = "Все данные о банкоматах успешно получены")
    @GetMapping()
    public ResponseEntity<List<AtmDTO>> showAllAtm() {
        List<AtmDTO> atmDTO = atmService.getAllAtm().stream()
                .map(atm -> modelMapper.map(atm, AtmDTO.class))
                .collect(Collectors.toList());
        return !atmDTO.isEmpty()? new ResponseEntity<>(atmDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Получение данных об одном банкомате",
            description = "Этот API позволяет получить данные об одном банкоматах"
    )
    @ApiResponse(responseCode = "200", description = "Данные о банкомате успешно получены")
    @GetMapping("/{id}")
    public ResponseEntity<AtmDTO> showAtm(@PathVariable(name = "id") Long id) {
        AtmDTO atmDTO = modelMapper.map(atmService.getAtmById(id), AtmDTO.class);
        if(atmDTO==null){
            throw new EntityNotFoundException("Atm с ID " + id + " в базе данных отсутствует");
        }
        return new ResponseEntity<>(atmDTO, HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый банкомат",
            description = "Этот API позволяет добавить новый банкомат")
    @ApiResponse(responseCode = "200", description = "Новый банкомат успешно добавлен")
    @PostMapping()
    public ResponseEntity<HttpStatus> createAtm(@RequestBody @Valid AtmDTO atmDTO) {
        atmService.createAtm(modelMapper.map(atmDTO, Atm.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующий банкомат",
            description = "Этот API позволяет обновить существующий банкомат")
    @ApiResponse(responseCode = "200", description = "Информация о банкомате успешно обновлена")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAtm(@RequestBody @Valid AtmDTO atmDTO,
                                            @PathVariable("id") Long id) {
        atmService.updateAtm(modelMapper.map(atmDTO, Atm.class), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить банкомат по ID",
            description = "Этот API позволяет удалить существующий банкомат по ID")
    @ApiResponse(responseCode = "200", description = "Информация о банкомате успешно удалена")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAtm(@PathVariable("id") Long id) {
        atmService.deleteAtm(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
