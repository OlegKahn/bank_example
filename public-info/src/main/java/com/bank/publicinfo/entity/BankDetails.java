package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  Класс c именем BankDetails, представляющим сущность деталей банка.
 *  Класс имеет следующие поля:
 *  id - первичный ключ для объекта, аннотация @Id, чтобы пометить его как первичный ключ, и
 *  @GeneratedValue указать, что он генерируется базой данных с использованием IDENTITY стратегии.
 *  bik - поле, представляющее БИК банка.
 *  inn - поле, представляющее ИНН банка.
 *  kpp - поле, представляющее КПП банка.
 *  corAccount - поле, представляющее кор.счет банка.
 *  city - строковое значение, представляющее город, в котором зарегистрирован юр. адрес банка.
 *  jointStockCompany - строковое значение, представляющее форму организации банка.
 *  name - строковое значение, представляющее название банка.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_details", schema = "public_bank_information")
public class BankDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "bik", unique = true)
    private Long bik;

    @NotNull
    @Column(name = "inn", unique = true)
    private Long inn;

    @NotNull
    @Column(name = "kpp", unique = true)
    private Long kpp;

    @NotNull
    @Column(name = "cor_account", unique = true)
    private Integer corAccount;

    @NotNull
    @NotBlank
    @Max(value = 180)
    @Column(name = "city")
    private String city;

    @NotNull
    @NotBlank
    @Max(value = 15)
    @Column(name = "joint_stock_company")
    private String jointStockCompany;

    @NotNull
    @NotBlank
    @Max(value = 80)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bankDetailsId", cascade = CascadeType.ALL)
    @AuditJoinTable
    private List<License> licenseList;

    @OneToMany(mappedBy = "bankDetailsId", cascade = CascadeType.ALL)
    @AuditJoinTable
    private List<Certificate> certificateList;


}
