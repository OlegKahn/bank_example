package com.bank.history.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс предназначен для хранения информации Exception
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryIncorrectData {

    private String info;
}
