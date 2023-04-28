package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.BankDetailsRepository;
import com.bank.publicinfo.service.interfacePublicInfo.BankDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {
    private final BankDetailsRepository bankDetailsRepository;

    /** Метод для создание нового объекта BankDetails
     *
     * @param bankDetails
     */
    @Override
    @Transactional
    public BankDetails createBankDetails(BankDetails bankDetails) {
        log.info("Сведения о деталях банка были внесены в базу данных");
        return bankDetailsRepository.save(bankDetails);
    }

    /** Метод для получения объекта BankDetails по его id
     *
     * @param id
     */
    @Override
    public BankDetails getBankDetailsById(Long id) {
        Optional<BankDetails> bankDetailsFromBD = bankDetailsRepository.findById(id);;
        if (bankDetailsFromBD.isEmpty()) {
            log.error("Сведения о деталях банка отсутствуют");
            throw new EntityNotFoundException("No bank details found by id");
        }
        BankDetails BankDetails1 = bankDetailsFromBD.get();
        log.info("Сведения о деталях банка с " + "id" + " получены из базы данных");
        return BankDetails1;
    }

    /** Метод для изменения уже существующего объекта BankDetails
     *
     * @param bankDetails, id
     */
    @Override
    @Transactional
    public BankDetails updateBankDetails(BankDetails bankDetails, Long id) {
        Optional<BankDetails> bankDetailsFromBD = bankDetailsRepository.findById(id);
        if (bankDetailsFromBD.isEmpty()) {
            log.error("Сведения о деталях банка отсутствуют");
            throw new EntityNotFoundException("No bank details found by id");
        }

        BankDetails bankDetails1 = bankDetailsFromBD.get();
        bankDetails1.setBik(bankDetails.getBik());
        bankDetails1.setInn(bankDetails.getInn());
        bankDetails1.setKpp(bankDetails.getKpp());
        bankDetails1.setCorAccount(bankDetails.getCorAccount());
        bankDetails1.setCity(bankDetails.getCity());
        bankDetails1.setJointStockCompany(bankDetails.getJointStockCompany());
        bankDetails1.setName(bankDetails.getName());
        log.info("Сведения о деталях банка были изменены в базе данных");
        return bankDetailsRepository.save(bankDetails1);
    }

    /** Метод для удаления объекта BankDetails
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteBankDetails(Long id) {
        Optional<BankDetails> bankDetailsFromDB = bankDetailsRepository.findById(id);
        if (bankDetailsFromDB.isEmpty()) {
            log.error("Сведения о деталях банка отсутствуют");
            throw new EntityNotFoundException("No bank details found by id");
        }
        log.info("Сведения о деталях банка были удалены из базы данных");
        bankDetailsRepository.deleteById(id);
    }

    /** Метод для получения списка всех объектов BankDetails
     *
     */

    @Override
    public List<BankDetails> getAllBankDetails() {
        List<BankDetails> allBankDetails = bankDetailsRepository.findAll();
        if (allBankDetails.isEmpty()) {
            log.error("Сведения о деталях банка отсутствуют");
            throw new EntityNotFoundException("bank details list is empty");
        }
        log.info("Были получены сведения обо всех банках и их деталях из базы данных");
        return allBankDetails;
    }
}
