package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 *  Класс c именем Branch, представляющим сущность отделения банка.
 *  Класс имеет следующие поля:
 *  id - первичный ключ для объекта, аннотация @Id, чтобы пометить его как первичный ключ, и
 *  @GeneratedValue указать, что он генерируется базой данных с использованием IDENTITY стратегии.
 *  address - строковое значение, представляющее адрес, по которому находится отделение банка.
 *  phoneNumber - поле, представляющее номер телефона отделения банка.
 *  city - строковое значение, представляющее город, в котором находится отделение банка.
 *  startOfWork - значение LocalTime, представляющее время начала работы отделения.
 *  endOfWork - значение LocalTime, представляющее время окончания работы отделения.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "branch", schema = "public_bank_information")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", unique = true)
    private Long phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "start_of_work")
    private LocalTime startOfWork;

    @Column(name = "end_of_work")
    private LocalTime endOfWork;

    @OneToMany(mappedBy = "branchId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @AuditJoinTable
    @JsonIgnore
    private List<Atm> atmList;

}
