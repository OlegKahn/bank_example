package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.BankDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BankDetailsService {
    BankDetails createBankDetails(BankDetails bankDetails);
    BankDetails getBankDetailsById(Long id);
    BankDetails updateBankDetails(BankDetails bankDetails, Long id);
    void deleteBankDetails(Long id);
    List<BankDetails> getAllBankDetails();
}
