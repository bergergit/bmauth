'use strict';

angular.module('megafunk.home', ['ngRoute'])

.controller('LoginCtrl', ['$scope', function($scope) {
	
	console.debug("in login controller");
	
	$scope.login = function() {
		console.debug('I want to log iiiin');
	}
}]);