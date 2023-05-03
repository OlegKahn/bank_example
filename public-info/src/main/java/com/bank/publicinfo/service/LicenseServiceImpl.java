package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.LicenseRepository;
import com.bank.publicinfo.service.interfacePublicInfo.LicenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    /** Метод для создание нового объекта License
     *
     * @param license
     */
    @Override
    @Transactional
    public License createLicense(License license) {
        log.info("Сведения о лицензии банка были внесены в базу данных");
        return licenseRepository.save(license);
    }

    /** Метод для получения объекта License по его id
     *
     * @param id
     */
    @Override
    public License getLicenseById(Long id) {
        Optional<License> licenseFromBD = licenseRepository.findById(id);
        if (licenseFromBD.isEmpty()) {
            log.error("Сведения о лицензии банка отсутствуют");
            throw new EntityNotFoundException("No license found by id");
        }
        License license1 = licenseFromBD.get();
        log.info("Сведения о лицензии банка с " + "id" + " получены из базы данных");
        return license1;
    }

    /** Метод для изменения уже существующего объекта License
     *
     * @param license, id
     */
    @Override
    @Transactional
    public License updateLicense(License license, Long id) {
        Optional<License> licenseFromBD = licenseRepository.findById(id);
        if (licenseFromBD.isEmpty()) {
            log.error("Сведения о лицензии банка отсутствуют");
            throw new EntityNotFoundException("No license found by id");
        }
        License license1 = licenseFromBD.get();
        log.info("Сведения о лицензии банка были изменены в базе данных");
        return licenseRepository.save(license);
    }

    /** Метод для удаления объекта License
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteLicense(Long id) {
        Optional<License> licenseFromBD = licenseRepository.findById(id);
        if (licenseFromBD.isEmpty()) {
            log.error("Сведения о лицензии банка отсутствуют");
            throw new EntityNotFoundException("No license found by id");
        }
        License license1 = licenseFromBD.get();
        log.info("Сведения о лицензии банка были удалены из базы данных");
        licenseRepository.deleteById(id);

    }

    /** Метод для получения списка всех объектов License
     *
     */
    @Override
    public List<License> getAllLicenses() {
        List<License> allLicenses = licenseRepository.findAll();
        if (allLicenses.isEmpty()) {
            log.error("Сведения о лицензиях банка отсутствуют");
            throw new EntityNotFoundException("license list is empty");
        }
        log.info("Были получены сведения обо всех лицензиях банка из базы данных");
        return allLicenses;
    }
}
