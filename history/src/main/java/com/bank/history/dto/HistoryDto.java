package com.bank.history.dto;

import com.bank.history.validator.CheckHistoryData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import java.util.Objects;

/**
 * The class is designed to store History data,
 * performs the Dto function
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "History Model Information")
public class HistoryDto {

    @CheckHistoryData
    @Schema(accessMode = Schema.AccessMode.READ_ONLY,
            description = "History Id", example = "123")
    private long id;

    @CheckHistoryData
    @Schema(description = "Transfer audit id", example = "123")
    private long transferAuditId;

    @CheckHistoryData
    @Schema(description = "Profile audit id", example = "123")
    private long profileAuditId;

    @CheckHistoryData
    @Schema(description = "Account audit id", example = "123")
    private long accountAuditId;

    @CheckHistoryData
    @Schema(description = "Anti fraud audit id", example = "123")
    private long antiFraudAuditId;

    @CheckHistoryData
    @Schema(description = "Public bank info audit id", example = "123")
    private long publicBankInfoAuditId;

    @CheckHistoryData
    @Schema(description = "Authorization audit id", example = "123")
    private long authorizationAuditId;


    /**
     * Standard comparison method
     * @param o object being compared
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HistoryDto dto = (HistoryDto) o;
        return id == dto.id && transferAuditId == dto.transferAuditId &&
                profileAuditId == dto.profileAuditId && accountAuditId ==
                dto.accountAuditId && antiFraudAuditId == dto.antiFraudAuditId &&
                publicBankInfoAuditId == dto.publicBankInfoAuditId &&
                authorizationAuditId == dto.authorizationAuditId;
    }

    /**
     * Standard Method for Generating a Hash Code
     * @return returns hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, transferAuditId, profileAuditId,
                accountAuditId, antiFraudAuditId, publicBankInfoAuditId,
                authorizationAuditId);
    }
}
