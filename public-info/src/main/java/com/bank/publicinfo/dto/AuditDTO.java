package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.Branch;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Класс AuditDto — это объект передачи данных (DTO), представляющий информацию об аудите.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация об аудите")
public class AuditDTO {

    @NotNull
    @Schema(description = "id")
    private Long id;
    @NotNull
    @Schema(description = "тип сущности")
    private String entityType;
    @NotNull
    @Schema(description = "тип операции")
    private String operationType;
    @NotNull
    @Schema(description = "кто создал")
    private String createdBy;
    @NotNull
    @Schema(description = "кто изменил")
    private String modifiedBy;
    @NotNull
    @Schema(description = "когда создан")
    private LocalDateTime createdAt;
    @NotNull
    @Schema(description = "когда изменен")
    private LocalDateTime modifiedAt;
    @NotNull
    @Schema(description = "json, заполняется при создании")
    private String newEntityJson;
    @NotNull
    @Schema(description = "json, заполняется при изменение и при сохранении")
    private String entityJson;
}
