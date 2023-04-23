package com.bank.publicinfo.service.audit;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.entity.RevInfo;
import com.bank.publicinfo.util.BeanUtil;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AuditListener implements RevisionListener {

    @Override
    @Transactional
    public void newRevision(Object revisionEntity) {

        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);

//-----------------------------ATM----------------------------------

        AuditQuery auditQueryAtm = AuditReaderFactory.get(entityManager).createQuery()
                .forRevisionsOfEntity(Atm.class, false, true);

        List<Object[]> resultListAtm = auditQueryAtm.getResultList();

        Object[] resultArrAtm = resultListAtm.get(resultListAtm.size() - 1);

        Atm modifyAtm = (Atm) resultArrAtm[0];
        RevInfo revInfoAtm = (RevInfo) resultArrAtm[1];
        RevisionType revisionTypeAtm = (RevisionType) resultArrAtm[2];
        revInfoAtm.setUsername("UserName");

        Audit auditAtm = new Audit();
        ZonedDateTime zonedDateTimeOfRevisionAtm = ZonedDateTime.ofInstant(Instant.ofEpochMilli(revInfoAtm.getTimestamp()),
                ZoneId.systemDefault());

        auditAtm.setEntityType(modifyAtm.getClass().getSimpleName());

        auditAtm.setOperationType(revisionTypeAtm.toString());
        if (revisionTypeAtm.toString().equals("ADD")) {
            auditAtm.setNewEntityJson(modifyAtm.toString());
            auditAtm.setCreatedAt(zonedDateTimeOfRevisionAtm);
            auditAtm.setCreatedBy(revInfoAtm.getUsername());
//            auditAtm.setNewEntityJson(((Atm) resultListAtm.get(resultListAtm.size() - 2)[0]).toString());
        } else {
            auditAtm.setEntityJson(modifyAtm.toString());
            auditAtm.setModifiedBy(revInfoAtm.getUsername());
            auditAtm.setModifiedAt(zonedDateTimeOfRevisionAtm);
        }

        auditAtm.setRevInfo(revInfoAtm);
        entityManager.persist(auditAtm);
        revInfoAtm.setAudit(auditAtm);
    }
}

