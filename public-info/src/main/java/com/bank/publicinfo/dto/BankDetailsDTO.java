package com.bank.publicinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Класс BankDetailsDTO — это объект передачи данных (DTO), представляющий информацию о деталях банка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о деталях банка")
public class BankDetailsDTO {

    @NotNull
    @Schema(description = "БИК банкомата")
    private Long bik;
    @NotNull
    @Schema(description = "ИНН банкомата")
    private Long inn;
    @NotNull
    @Schema(description = "КПП банкомата")
    private Long kpp;
    @NotNull
    @Schema(description = "Кор.счет банкомата")
    private Integer corAccount;
    @NotNull
    @NotBlank
    @Schema(description = "Город, в котором зарегистрирован юр. адрес банка")
    private String city;
    @NotNull
    @NotBlank
    @Schema(description = "Акционерное общество")
    private String jointStockCompany;
    @NotNull
    @NotBlank
    @Schema(description = "Название банка")
    private String name;

}
