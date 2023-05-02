package com.bank.history.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Класс служит для изменения, точнее добавления полей в таблицу revinfo
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@RevisionEntity(HistoryListener.class)
@Table(name = "REVINFO", schema = "history")
public class HistoryRevInfo extends DefaultRevisionEntity {

    private String username;

}
