package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.Branch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Класс AtmDto — это объект передачи данных (DTO), представляющий информацию о банкомате.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о банкомате")
public class AtmDTO {

    @NotNull
    @Schema(description = "Адрес банкомата")
    private String address;
    @NotNull
    @Schema(description = "Начало работы банкомата")
    private LocalTime startOfWork;
    @NotNull
    @Schema(description = "Конец работы банкомата")
    private LocalTime endOfWork;
    @Schema(description = "Работает ли банкомат круглосуточно")
    private Boolean allHours;
    @Schema(description = "Ссылка на отделение, где стоит банкомат")
    private Branch branchId;


}
