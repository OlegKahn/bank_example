package com.bank.history.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// storage for exception's information
@NoArgsConstructor
@Getter
@Setter
public class HistoryIncorrectData {

    private String info;
}
