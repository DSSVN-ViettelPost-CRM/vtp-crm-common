package vtp.crm.common.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class QuerySection {

    private String prepareSection;

    private String selectSection;

    private String fromSection;

    private String whereSection;

    private String groupBySection;

    private String havingSection;

    private String orderBySection;

    public String generateGetRecordsSql() {
        return StreamEx.of(prepareSection, selectSection, fromSection, whereSection, groupBySection, havingSection, orderBySection)
                .map(StringUtils::trimToNull)
                .nonNull()
                .joining(" ");
    }

    public String generateCountAllSql() {
        String sql = StreamEx.of(prepareSection, "select 1", fromSection, whereSection, groupBySection, havingSection)
                .map(StringUtils::trimToNull)
                .nonNull()
                .joining(" ");
        return "select count(1) from (" + sql + ") as foo";
    }

}
