package org.rippleosi.patient.problems.store;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.rippleosi.common.exception.ConfigurationException;
import org.rippleosi.patient.problems.model.ProblemDetails;

/**
 */
public class NotConfiguredProblemStore implements ProblemStore {

    @Override
    public String getSource() {
        return "not configured";
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void create(@Header("patientId") String patientId, @Body ProblemDetails problem) {
        throw ConfigurationException.unimplementedTransaction(ProblemStore.class);
    }

    @Override
    public void update(@Header("patientId") String patientId, @Body ProblemDetails problem) {
        throw ConfigurationException.unimplementedTransaction(ProblemStore.class);
    }
}