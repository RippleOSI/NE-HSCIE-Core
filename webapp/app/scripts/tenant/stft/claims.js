'use strict';

angular.module('rippleDemonstrator')
  .value("claims", {
    "sub": "28AD8576-1948-4C84-8B5E-55FB7EE027CE",
    "given_name": "Bob",
    "family_name": "Smith",
    "email": "bob.smith@gmail.com",
    "scope": {
      "homeView": "main-search",
      "autoAdvancedSearch": true,
      "patientSummaryView": "landing",
      "searchBarEnabled": false,
      "roleConfirmationRequired": false,
      "navBar": {
        "patientSummaryEnabled": true,
        "alertsEnabled": true,
        "keyworkersEnabled": true,
        "contactsEnabled": true,
        "diagnosisEnabled": true,
        "problemsEnabled": true,
        "allergiesEnabled": false,
        "medicationsEnabled": true,
        "ordersEnabled": false,
        "resultsEnabled": false,
        "proceduresEnabled": false,
        "referralsEnabled": true,
        "appointmentsEnabled": true,
        "transfersEnabled": true,
        "careplansEnabled": false,
        "mdtEnabled": false,
        "imagesEnabled": false,
        "tagsEnabled": false
      },
      "patientSource": "tie"
    },
    "tenant_id": "STFT",
    "tenant_name": "South Tyneside NHS Trust",
    "role": "idcr"
  });
