package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Класс BranchDTO — это объект передачи данных (DTO), представляющий информацию о сертификате банка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о сертификате банка")
public class CertificateDTO {

    @NotNull
    @Schema(description = "Фотография сертификата")
    byte[] photo;
    @NotNull
    @Schema(description = "Ccылка на реквизиты банка")
    private BankDetails bankDetailsId;

}
