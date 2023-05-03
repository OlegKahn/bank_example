package com.bank.publicinfo.entity;

import com.bank.publicinfo.service.audit.AuditListener;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Data
@Entity
@RevisionEntity(AuditListener.class)
@Table(name = "revinfo", schema = "public_bank_information")
public class RevInfo {
    @Id
    @RevisionNumber
    @GeneratedValue(generator = "CustomerAuditRevisionSeq")
    @SequenceGenerator(name = "CustomerAuditRevisionSeq", sequenceName = "customer_audit_revision_seq",
            schema = "public_bank_information", allocationSize = 1)
    private int id;
    @RevisionTimestamp
    private long timestamp;
    private String username;

    @OneToOne
    @JoinColumn(name = "audit_id", referencedColumnName = "id")
    private Audit audit;

}

