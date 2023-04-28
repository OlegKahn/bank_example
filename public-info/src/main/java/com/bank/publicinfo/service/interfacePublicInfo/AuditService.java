package com.bank.publicinfo.service.interfacePublicInfo;

import com.bank.publicinfo.entity.Audit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuditService {
    Audit createAudit(Audit audit);
    Audit getAuditById(Long id);
    Audit updateAudit(Audit audit, Long id);
    void deleteAudit(Long id);
    List<Audit> getAllAudit();

}
