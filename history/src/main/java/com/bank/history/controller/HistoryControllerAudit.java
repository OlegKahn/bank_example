package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Rest API class for receiving change information
 * Methods:
 * getAllAuditById - gets all changes of a certain History
 * @see HistoryControllerAudit#getAllAuditById(long)
 *
 *  getLastAuditById - gets the latest change in History
 * @see HistoryControllerAudit#getLastAuditById(long)
 *
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/audit")
@Tag(name = "History changes info", description = "History changes management APIs")
public class HistoryControllerAudit {

    private final HistoryService historyService;

    private final ModelMapper modelMapper;

    /**
     * Method to get all changes
     * specific History via id
     *
     * @param id History from which we are looking for all changes
     * @return Map<RevisionMetadata<Long>, HistoryDto>
     */
    @Operation(summary = "This is to fetch All the History changes stored in Db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Changes detail of All the History",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Page not found",
                    content = @Content)
    })
    @Timed("gettingAllAuditById")
    @GetMapping("/all/{id}")
    public Map<RevisionMetadata<Long>, HistoryDto> getAllAuditById(@PathVariable long id) {
        final Map<RevisionMetadata<Long>, HistoryDto> allAuditById
                = historyService.getAllAuditById(id).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry
                        -> modelMapper.map(entry.getValue(), HistoryDto.class)));
        log.info("get all change history by history id = " + id);
        return allAuditById;
    }

    /**
     * Method to get the last change
     * specific History via id
     *
     * @param id History from which we are looking for the last change
     * @return Map<RevisionMetadata<Long>, HistoryDto>
     */
    @Operation(summary = "This is to fetch Last the History change stored in Db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Last change the History",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Page not found",
                    content = @Content)
    })
    @Timed("gettingLastAuditById")
    @GetMapping("/last/{id}")
    public Map<RevisionMetadata<Long>, HistoryDto> getLastAuditById(@PathVariable long id) {
        final Map<RevisionMetadata<Long>, HistoryDto> lastAuditById
                = historyService.getLastAuditById(id).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry
                        -> modelMapper.map(entry.getValue(), HistoryDto.class)));
        log.info("get last change history by history id = " + id);
        return lastAuditById;
    }
}
