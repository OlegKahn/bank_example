package com.bank.history.controller;


import com.bank.history.dto.HistoryDto;
import com.bank.history.model.History;
import com.bank.history.service.HistoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@AllArgsConstructor
public class HistoryController {

    private final ModelMapper modelMapper;

    private final HistoryService service;


    // get all history like dto
    @GetMapping("/")
    public List<HistoryDto> getAll() {
        return service.getAll().stream().map(h -> modelMapper.map(h, HistoryDto.class)).toList();
    }

    // get history by id like dto
    @GetMapping("/{id}")
    public ResponseEntity<HistoryDto> getById(@PathVariable long id) {
        History history = service.getById(id);
        HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        return ResponseEntity.ok().body(dto);
    }

    // save new history
    @PostMapping("/")
    public ResponseEntity<HistoryDto> saveNew(@RequestBody HistoryDto historyDto) {
        History historyRequest = modelMapper.map(historyDto, History.class);
        History history = service.saveNew(historyRequest);
        HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // change existent history
    @PutMapping("/")
    public ResponseEntity<HistoryDto> saveOld(@RequestBody HistoryDto historyDto) {
        History historyRequest = modelMapper.map(historyDto, History.class);
        History history = service.saveNew(historyRequest);
        HistoryDto dto = modelMapper.map(history, HistoryDto.class);
        return ResponseEntity.ok().body(dto);

    }

    // delete history by id
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
