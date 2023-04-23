package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.CertificateRepository;
import com.bank.publicinfo.service.interfacePublicInfo.CertificateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    /** Метод для создание нового объекта Certificate
     *
     * @param certificate
     */
    @Override
    @Transactional
    public Certificate createCertificate(Certificate certificate) {
        log.info("Сведения о сертификате банка были внесены в базу данных");
        return certificateRepository.save(certificate);
    }

    /** Метод для получения объекта Certificate по его id
     *
     * @param id
     */
    @Override
    public Certificate getCertificatedById(Long id) {
        Optional<Certificate> certificateFromBD = certificateRepository.findById(id);
        if (certificateFromBD.isEmpty()) {
            log.error("Сведения о сертификате банка отсутствуют");
            throw new EntityNotFoundException("No certificate found by id");
        }
        Certificate certificate1 = certificateFromBD.get();
        log.info("Сведения о сертификате банка с " + "id" + " получены из базы данных");
        return certificate1;
    }

    /** Метод для изменения уже существующего объекта Certificate
     *
     * @param certificate, id
     */
    @Override
    @Transactional
    public Certificate updateCertificate(Certificate certificate, Long id) {
        Optional<Certificate> certificateFromBD = certificateRepository.findById(id);
        if (certificateFromBD.isEmpty()) {
            log.error("Сведения о сертификате банка отсутствуют");
            throw new EntityNotFoundException("No certificate found by id");
        }
        Certificate certificate1 = certificateFromBD.get();
        log.info("Сведения о сертификате банка были изменены в базе данных");
        return certificateRepository.save(certificate);
    }

    /** Метод для удаления объекта Certificate
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        Optional<Certificate> certificateFromBD = certificateRepository.findById(id);
        if (certificateFromBD.isEmpty()) {
            log.error("Сведения о сертификате банка отсутствуют");
            throw new EntityNotFoundException("No certificate found by id");
        }
        Certificate certificate1 = certificateFromBD.get();
        log.info("Сведения о сертификате банка были удалены из базы данных");
        certificateRepository.deleteById(id);
    }

    /** Метод для получения списка всех объектов Certificate
     *
     */
    @Override
    public List<Certificate> getAllCertificates() {
        List<Certificate> allCertificates = certificateRepository.findAll();
        if (allCertificates.isEmpty()) {
            log.error("Сведения о сертификах банка отсутствуют");
            throw new EntityNotFoundException("certificate list is empty");
        }
        log.info("Были получены сведения обо всех сертификатах банка из базы данных");
        return allCertificates;
    }
}
