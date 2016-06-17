'use strict';

angular.module('rippleDemonstrator')
  .value("claims", {
    "sub": "28AD8576-1948-4C84-8B5E-55FB7EE027CE",
    "given_name": "Bob",
    "family_name": "Smith",
    "email": "bob.smith@gmail.com",
    "username" : "bob.smith",
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
        "diagnosisEnabled": false,
        "problemsEnabled": true,
        "allergiesEnabled": false,
        "medicationsEnabled": false,
        "ordersEnabled": false,
        "resultsEnabled": false,
        "proceduresEnabled": false,
        "referralsEnabled": true,
        "appointmentsEnabled": true,
        "transfersEnabled": false,
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
