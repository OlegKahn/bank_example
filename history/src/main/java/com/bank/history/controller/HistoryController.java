package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import com.bank.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * Основной Rest API класс, возвращает HistoryDto
 * Методы:
 * getAll - возвращает List со всеми HistoryDto
 * @see HistoryController#getAll()
 *
 * getById - получает id и возвращает ResponseEntity с телом HistoryDto
 * @see HistoryController#getById(long)
 *
 * saveNew - получает HistoryDto и сохраняет его в БД
 * @see HistoryController#saveNew(HistoryDto)
 *
 * saveOld - получает существующий HistoryDto
 * с измененными данными и сохранят
 * @see HistoryController#saveOld(HistoryDto)
 *
 * delete - получает id и удаляет
 * @see HistoryController#delete(long)
 *
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "History API", description = "History management APIs")
public class HistoryController {

    private final ModelMapper modelMapper;

    private final HistoryService service;


    /**
     * Метод для получения все History,
     * превращения в HistoryDto
     * и сохранения в List
     *
     * @return возвращает List<HistoryDto>
     */
    @Operation(summary = "This is to fetch All the Histories stored in Db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of All the Histories",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Page not found",
                    content = @Content)
    })
    @GetMapping("/")
    public List<HistoryDto> getAll() {
        log.info("get all history");
        return service.getAll().stream().map(h -> modelMapper.map(h, HistoryDto.class)).toList();
    }


    /**
     * Метод для получения History из БД
     * и превращение его в HistoryDto
     *
     * @param id номер ячейки в БД
     * @return ResponseEntity<HistoryDto>
     */
    @Operation(summary = "This is to get the details of particular  Histories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = " History details fetched from database",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = " Page Not Found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDto> getById(
            @Parameter(description = "Find history by id", required = true)
            @RequestParam(defaultValue = "1")
            @PathVariable long id) {
        final History history = service.getById(id);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("get history by id = " + id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Метод для сохранения полученного HistoryDto
     * в БД как History
     *
     * @param historyDto полученный обьект
     * @return ResponseEntity<HistoryDto>
     */
    // save new history
    @Operation(summary = "This is to add the Histories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = " History details saved in database",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = " Page Not Found",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<HistoryDto> saveNew(@RequestBody HistoryDto historyDto) {
        final History historyRequest = modelMapper.map(historyDto, History.class);
        final History history = service.saveNew(historyRequest);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("save new history = " + history);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Метод для изменения существующего History в БД
     * с помощью получения HistoryDto
     * и превращения его History и сохранения
     * @param historyDto полученный обьект
     * @return ResponseEntity<HistoryDto>
     */
    // change existent history
    @Operation(summary = "This is to update the Histories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = " History details updated in database",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = " Page Not Found",
                    content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<HistoryDto> saveOld(@RequestBody HistoryDto historyDto) {
        final History historyRequest = modelMapper.map(historyDto, History.class);
        final History history = service.saveOld(historyRequest);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("update history information id = " + historyDto.getId());
        return ResponseEntity.ok().body(dto);

    }

    /**
     * Метод для удаления History из БД
     * с помощью полученного id
     *
     * @param id History который будет удален
     */
    // delete history by id
    @Operation(summary = "This is to delete the Histories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = " History deleted from the database",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = " Page Not Found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Delete history by id", required = true)
            @PathVariable long id) {
        service.delete(id);
        log.info("delete history by id = " + id);
    }
}
