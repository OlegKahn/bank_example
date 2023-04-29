package com.bank.history.entity;

import com.bank.history.validator.CheckHistoryData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import org.hibernate.envers.Audited;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import java.util.Objects;

/**
 * Основной класс-сущность хранимый в БД
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "history", name = "history")
@Audited
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "history_generator", sequenceName = "history_seq", schema = "history", allocationSize = 1)
    private long id;

    @CheckHistoryData
    @Column(name = "transfer_audit_id")
    private long transferAuditId;

    @CheckHistoryData
    @Column(name = "profile_audit_id")
    private long profileAuditId;

    @CheckHistoryData
    @Column(name = "account_audit_id")
    private long accountAuditId;

    @CheckHistoryData
    @Column(name = "anti_fraud_audit_id")
    private long antiFraudAuditId;

    @CheckHistoryData
    @Column(name = "public_bank_info_audit_id")
    private long publicBankInfoAuditId;

    @CheckHistoryData
    @Column(name = "authorization_audit_id")
    private long authorizationAuditId;


    /**
     * Стандартный метод сравнения
     * @param o объект который сравнивают
     * @return true если равны, false если нет
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final History history = (History) o;
        return id == history.id && transferAuditId == history.transferAuditId &&
                profileAuditId == history.profileAuditId && accountAuditId ==
                history.accountAuditId && antiFraudAuditId == history.antiFraudAuditId &&
                publicBankInfoAuditId == history.publicBankInfoAuditId &&
                authorizationAuditId == history.authorizationAuditId;
    }

    /**
     * Стандартный метод для создания хеш-кода
     * @return возвращает хеш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, transferAuditId, profileAuditId,
                accountAuditId, antiFraudAuditId, publicBankInfoAuditId,
                authorizationAuditId);
    }
}
