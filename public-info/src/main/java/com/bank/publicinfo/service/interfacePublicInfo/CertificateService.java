package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.Certificate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CertificateService {
    Certificate createCertificate(Certificate certificate);
    Certificate getCertificatedById(Long id);
    Certificate updateCertificate(Certificate certificate, Long id);
    void deleteCertificate(Long id);
    List<Certificate> getAllCertificates();
}
