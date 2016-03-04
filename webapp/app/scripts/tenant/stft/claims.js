'use strict';

angular.module('rippleDemonstrator')
  .value("claims", {
    "sub": "28AD8576-1948-4C84-8B5E-55FB7EE027CE",
    "given_name": "Bob",
    "family_name": "Smith",
    "email": "bob.smith@gmail.com",
    "scope": {
      "homeView": "search",
      "patientSummaryView": "landing",
      "searchBarEnabled": false,
      "roleConfirmationRequired": false,
      "navBar": {
          "patientSummaryEnabled": true,
          "keyworkersEnabled": true,
          "contactsEnabled": false,
          "diagnosisEnabled": false,
          "allergiesEnabled": false,
          "medicationsEnabled": false,
          "ordersEnabled": false,
          "resultsEnabled": false,
          "proceduresEnabled": false,
          "referralsEnabled": false,
          "appointmentsEnabled": false,
          "transfersEnabled": false,
          "careplansEnabled": false,
          "mdtEnabled": false,
          "imagesEnabled": false
      },
      "patientSource": "tie"
    },
    "tenant_id": "STFT",
    "tenant_name": "South Tyneside NHS Trust",
    "role": "idcr"
});
