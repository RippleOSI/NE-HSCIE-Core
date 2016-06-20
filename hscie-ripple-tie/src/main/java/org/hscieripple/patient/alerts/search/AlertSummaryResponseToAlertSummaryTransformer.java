package org.hscieripple.patient.alerts.search;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.alerts.AlertsSummaryResponse;
import org.hscieripple.patient.alerts.AlertsSummaryResultRow;
import org.hscieripple.patient.alerts.model.AlertSummary;
import org.rippleosi.common.util.DateFormatter;

public class AlertSummaryResponseToAlertSummaryTransformer implements Transformer<AlertsSummaryResultRow, AlertSummary> {

    @Override
    public AlertSummary transform(final AlertsSummaryResultRow result) {
        final Date dateTime = DateFormatter.toDate(result.getDateTime());

        final AlertSummary summary = new AlertSummary();

        summary.setSource(result.getDataSourceName());
        summary.setSourceId(result.getSourceID());
        summary.setAlertType(result.getAlertType());
        summary.setDateTime(dateTime);

        return summary;
    }
}
