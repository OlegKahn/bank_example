package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.License;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LicenseService {
    License createLicense(License license);
    License getLicenseById(Long id);
    License updateLicense(License license, Long id);
    void deleteLicense(Long id);
    List<License> getAllLicenses();
}
