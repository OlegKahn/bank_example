package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Класс BranchDTO — это объект передачи данных (DTO), представляющий информацию об лицензии банка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о лицензии банка")
public class LicenseDTO {

    @NotNull
    @Schema(description = "Фотография лицензии")
    byte[] photo;
    @NotNull
    @Schema(description = "Ccылка на реквизиты банка")
    private BankDetails bankDetailsId;

}
