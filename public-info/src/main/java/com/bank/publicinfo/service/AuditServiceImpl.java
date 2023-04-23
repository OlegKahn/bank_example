package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.exception.EntityNotFoundException;
import com.bank.publicinfo.repository.AuditRepository;
import com.bank.publicinfo.service.interfacePublicInfo.AuditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    /** Метод для создание нового объекта Audit
     *
     * @param audit
     */
    @Override
    @Transactional
    public Audit createAudit(Audit audit) {
        log.info("Сведения об аудите были внесены в базу данных");
        return auditRepository.save(audit);
    }

    /** Метод для получения объекта Audit по его id
     *
     * @param id
     */
    @Override
    public Audit getAuditById(Long id) {
        Optional<Audit> auditFromBD = auditRepository.findById(id);
        if (auditFromBD.isEmpty()) {
            log.error("Сведения об аудите отсутствуют");
            throw new EntityNotFoundException("No audit found by id");
        }
        Audit audit1 = auditFromBD.get();
        log.info("Сведения об отделении банка с " + "id" + " получены из базы данных");
        return audit1;
    }

    /** Метод для изменения уже существующего объекта Audit
     *
     * @param audit, id
     */
    @Override
    @Transactional
    public Audit updateAudit(Audit audit, Long id) {
        Optional<Audit> auditFromBD = auditRepository.findById(id);
        if (auditFromBD.isEmpty()) {
            log.error("Сведения об аудите отсутствуют");
            throw new EntityNotFoundException("No audit found by id");
        }
        Audit audit1 = auditFromBD.get();
        log.info("Сведения об аудите были изменены в базе данных");
        return auditRepository.save(audit1);
    }

    /** Метод для удаления объекта Audit
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteAudit(Long id) {
        Optional<Audit> auditFromDB = auditRepository.findById(id);
        if (auditFromDB.isEmpty()) {
            log.error("Сведения об аудите отсутствуют");
            throw new EntityNotFoundException("No audit found by id");
        }
        log.info("Сведения об аудите были удалены из базы данных");
        auditRepository.deleteById(id);
    }

    /** Метод для получения списка всех объектов Audit
     *
     */
    @Override
    public List<Audit> getAllAudit() {
        List<Audit> allAudit = auditRepository.findAll();
        if (allAudit.isEmpty()) {
            log.error("Сведения об аудите отсутствуют");
            throw new EntityNotFoundException("audit list is empty");
        }
        log.info("Были получены сведения об аудите из базы данных");
        return allAudit;
    }
}
