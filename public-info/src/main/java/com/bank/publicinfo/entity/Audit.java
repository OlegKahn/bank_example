package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 *  Класс c именем Audit, представляющим сущность аудита.
 *  Класс имеет следующие поля:
 *  id - первичный ключ для объекта, аннотация @Id, чтобы пометить его как первичный ключ, и
 *  @GeneratedValue указать, что он генерируется базой данных с использованием IDENTITY стратегии.
 *  entityType - строковое значение, представляющее тип проверенной сущности.
 *  operationType - строковое значение, представляющее тип проверенной операции.
 *  createdBy - строковое значение, представляющее имя пользователя, создавшего объект.
 *  modifiedBy - строковое значение, представляющее имя пользователя, изменившего объект.
 *  createdAt - значение LocalDateTime, представляющие дату и время создания объекта.
 *  modifiedAt - значение LocalDateTime, представляющие дату и время изменения объекта.
 *  newEntityJson - строковое значение, представляющее данные json при создании нового объекта.
 *  entityJson - строковое значение, представляющее данные json при изменении существующего объекта.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audit", schema = "public_bank_information")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "entity_type")
    private String entityType;

    @NotNull
    @NotBlank
    @Column(name = "operation_type")
    private String operationType;

    @NotNull
    @NotBlank
    @Column(name = "created_by")
    private String createdBy;

    @NotNull
    @NotBlank
    @Column(name = "modified_by")
    private String modifiedBy;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    @NotNull
    @NotBlank
    @Column(name = "new_entity_json")
    private String newEntityJson;

    @NotNull
    @NotBlank
    @Column(name = "entity_json")
    private String entityJson;

    @OneToOne(mappedBy = "audit")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private RevInfo revInfo;

}
