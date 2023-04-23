package com.bank.publicinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Класс BranchDTO — это объект передачи данных (DTO), представляющий информацию об отделении банка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация об отделении банка")
public class BranchDTO {

    @NotNull
    @Schema(description = "id")
    private Long id;
    @NotNull
    @Schema(description = "Адрес отделения")
    private String address;
    @NotNull
    @Schema(description = "Номер телефона отделения")
    private Long phoneNumber;
    @NotNull
    @Schema(description = "Город в котором находится отделение")
    private String city;
    @NotNull
    @Schema(description = "Начало работы отделения")
    private LocalTime startOfWork;
    @NotNull
    @Schema(description = "Конец работы отделения")
    private LocalTime endOfWork;

}
