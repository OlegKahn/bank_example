package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.Atm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AtmService {
    Atm createAtm(Atm atm);
    Atm getAtmById(Long id);
    Atm updateAtm(Atm atm, Long id);
    void deleteAtm(Long id);
    List<Atm> getAllAtm();

}
