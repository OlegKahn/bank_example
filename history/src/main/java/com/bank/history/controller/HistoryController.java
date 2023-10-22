package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import com.bank.history.service.HistoryService;
import io.micrometer.core.annotation.Timed;
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
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * Main Rest API class, returns HistoryDto
 * Methods:
 * getAll - returns a List with all HistoryDto
 * @see HistoryController#getAll()
 *
 * getById - gets id and returns ResponseEntity with body HistoryDto
 * @see HistoryController#getById(long)
 *
 * saveNew - gets HistoryDto and saves it to the database
 * @see HistoryController#saveNew(HistoryDto)
 *
 * saveOld - gets the existing HistoryDto
 * with changed data and will save
 * @see HistoryController#saveOld(HistoryDto)
 *
 * delete - gets id and deletes
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
     * Method to get all History,
     * turning into HistoryDto
     * and saving to List
     *
     * @return returns List<HistoryDto>
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
    @Timed("gettingAllHistory")
    @GetMapping("/")
    public List<HistoryDto> getAll() {
        log.info("get all history");
        return service.getAll().stream().map(h -> modelMapper.map(h, HistoryDto.class)).toList();
    }


    /**
     * Method for getting History from the database
     * and turning it into HistoryDto
     *
     * @param id cell number in the database
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
    @Timed("gettingById")
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDto> getById(
            @Parameter(description = "Find history by id", required = true)
            @PathVariable long id) {
        final History history = service.getById(id);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("get history by id = " + id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Method for saving the received HistoryDto
     * in the database as History
     *
     * @param historyDto received object
     * @return ResponseEntity<HistoryDto>
     */
    // save new history
    @Operation(summary = "This is to add the Histories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = " History details saved in database",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = " Page Not Found",
                    content = @Content)
    })
    @Timed("savingNewHistory")
    @PostMapping("/")
    public ResponseEntity<HistoryDto> saveNew(@RequestBody HistoryDto historyDto) {
        final History historyRequest = modelMapper.map(historyDto, History.class);
        final History history = service.saveNew(historyRequest);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("save new history = " + history);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Method for changing existing History in the database
     * by getting HistoryDto
     * and turning it into History and preserving
     * @param historyDto received object
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
    @Timed("savingOldHistory")
    @PutMapping("/")
    public ResponseEntity<HistoryDto> saveOld(@RequestBody HistoryDto historyDto) {
        final History historyRequest = modelMapper.map(historyDto, History.class);
        final History history = service.saveOld(historyRequest);
        final HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        log.info("update history information id = " + historyDto.getId());
        return ResponseEntity.ok().body(dto);

    }

    /**
     * Method for removing History from the database
     * using the received id
     *
     * @param id History which will be deleted
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
    @Timed("deletingHistory")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Delete history by id", required = true)
            @PathVariable long id) {
        service.delete(id);
        log.info("delete history by id = " + id);
    }
}
