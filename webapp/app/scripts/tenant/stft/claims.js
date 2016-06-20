'use strict';

angular.module('rippleDemonstrator')
  .value("claims", {
    "organisation" : "hscie",
    "scope": {
      "homeView": "main-search",
      "autoAdvancedSearch": true,
      "patientSummaryView": "landing",
      "searchBarEnabled": false,
      "roleConfirmationRequired": false,
      "navBar": {
        "patientSummaryEnabled": true,
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
    "tenant_name": "South Tyneside NHS Trust"
  });
