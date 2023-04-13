package com.bank.history.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "history", name = "history")
public class History {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "transfer_audit_id")
    private long transferAuditId;

    @NotNull
    @Column(name = "profile_audit_id")
    private long profileAuditId;

    @NotNull
    @Column(name = "account_audit_id")
    private long accountAuditId;

    @NotNull
    @Column(name = "anti_fraud_audit_id")
    private long antiFraudAuditId;

    @NotNull
    @Column(name = "public_bank_info_audit_id")
    private long publicBankInfoAuditId;

    @NotNull
    @Column(name = "authorization_audit_id")
    private long authorizationAuditId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        History history = (History) o;
        return getId() != 0 && Objects.equals(getId(), history.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
