'use strict';

angular.module('bmauth.applications', [])

.controller('ApplicationsCtrl', ['$scope', function($scope) {
	
	console.debug("in applications controller");
	
	$scope.application = {"active": true, "testMode": "0"};
	
}]);