package org.hscieripple.patient.alerts.search;

import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.hscieripple.patient.alerts.AlertsDetailsResponse;
import org.hscieripple.patient.alerts.model.AlertDetails;
import org.rippleosi.common.util.DateFormatter;

public class AlertDetailsResponseToAlertDetailsTransformer implements Transformer<AlertsDetailsResponse, AlertDetails> {

    @Override
    public AlertDetails transform(final AlertsDetailsResponse result) {
        final Date dateTime = DateFormatter.toDate(result.getDateTime());

        final AlertDetails details = new AlertDetails();

        details.setSource(result.getDataSourceName());
        details.setSourceId(result.getSourceID());
        details.setAlertType(result.getAlertType());
        details.setDateTime(dateTime);

        return details;
    }
}
