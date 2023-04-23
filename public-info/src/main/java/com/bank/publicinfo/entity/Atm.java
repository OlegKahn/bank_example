package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;

/**
 *  Класс c именем AtmEntity, представляющим сущность банкомата.
 *  Класс имеет следующие поля:
 *  id - первичный ключ для объекта, аннотация @Id, чтобы пометить его как первичный ключ, и
 *  @GeneratedValue указать, что он генерируется базой данных с использованием IDENTITY стратегии.
 *  address - строковое значение, представляющее адрес банкомата.
 *  startOfWork - значение LocalTime, представляющее время начала работы банкомата.
 *  endOfWork - значение LocalTime, представляющее время окончания работы банкомата
 *  allHours - булевское значение, работает ли банкомат круглосуточно (true/false)
 *  branchId - ссылка на отделение, где стоит банкомат, Branch поле с аннотацией представляющее связанный объект отделения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "atm", schema = "public_bank_information")
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @DateTimeFormat(pattern = "HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "start_of_work")
    private LocalTime startOfWork;

    @DateTimeFormat(pattern = "HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "end_of_work")
    private LocalTime endOfWork;

    @Column(name = "all_hours")
    private Boolean allHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branchId;

}
