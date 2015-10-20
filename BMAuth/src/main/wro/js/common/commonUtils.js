angular.module('bmauth.common', [])

.factory('formUtils', ['$translate', function($translate) {
	var formUtils = {
		/**
		 * Handle form errors
		 * Displays all error messages, appended in the nodeId
		 * Will use the translation prefix to get the label of the field with error
		 */
		handleFormErrors: function(error, nodeId, translationPrefix) {
			 if (error && error.data && error.data.code === 0) {
				 // getting the node with jquery, and clearing it
				 $node = $('#' + nodeId).html('');
				 var errorsList = $('<ul>');
				 console.debug('error', error);
				 for (var i = 0; i < error.data.fieldErrors.length; i++) {
					 // adding each new error into a <il> and appending to the <ul> created above
					 var errorItem = $('<li>');
					 var errorMessage = $translate.instant('fieldErrors.message', {
						 field: $translate.instant(translationPrefix + '.' + error.data.fieldErrors[i].fieldName),
						 message: $translate.instant('fieldErrors.' + error.data.fieldErrors[i].errorCode)
					 });
					 errorItem.text(errorMessage).appendTo(errorsList);
				 }
				 // appending the error list to the node, and showing it
				 errorsList.appendTo($node);
				 $node.removeClass('hidden');
			 }
		}
	}
	
	return formUtils;
}]);