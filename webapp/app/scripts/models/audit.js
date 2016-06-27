'use strict';

angular.module('rippleDemonstrator')
  .factory('Audit', function ($window) {

    var audit = function (attributes) {
      var self = this;
      _.extend(this, attributes);

      self.requesterAction = convertAction(attributes.action);
    };
    
    var convertAction = function(rawAction) {
    	
    	var convertedAction;
    	
      	switch(rawAction) {
			case "CREATE" : convertedAction = "Create"; break;
			case "UPDATE"  : convertedAction =  "Edit"; break;
			case "READ"  : convertedAction =  "View"; break;
			case "NULL" : convertedAction =  "Unknown"; break;
			default     : convertedAction =  "Unknown"; break;
      	}; 
      	
      	return convertedAction;
      };         

    return audit;

  });
