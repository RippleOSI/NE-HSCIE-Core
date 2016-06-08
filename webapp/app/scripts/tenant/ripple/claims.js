'use strict';

angular.module('rippleDemonstrator')
  .value("claims", {
    "sub": "28AD8576-1948-4C84-8B5E-55FB7EE027CE",
    "given_name": "Bob",
    "family_name": "Smith",
    "email": "bob.smith@gmail.com",
    "scope": {
      "homeView": "main-search",
      "autoAdvancedSearch": false,
      "searchBarEnabled": true,
      "roleConfirmationRequired": true,
      "navBar": {
        "patientSummaryEnabled": true,
        "keyworkersEnabled": false,
        "contactsEnabled": true,
        "diagnosisEnabled": true,
        "allergiesEnabled": true,
        "medicationsEnabled": true,
        "ordersEnabled": true,
        "resultsEnabled": true,
        "proceduresEnabled": true,
        "referralsEnabled": true,
        "appointmentsEnabled": true,
        "transfersEnabled": true,
        "careplansEnabled": true,
        "mdtEnabled": true,
        "imagesEnabled": true,
        "tagsEnabled": true
      }
    },
    "tenant_id": "Ripple",
    "tenant_name": "Ripple Demonstrator",
    "role": "idcr"
  });
