'use strict';

angular.module('bmauth.login', [])

.controller('LoginCtrl', ['$scope', function($scope) {
	
	console.debug("in login controller");
	
	$scope.submitLogin = function() {
		console.debug('I want to log iiiin');
	}
}])

.controller('SignupCtrl', ['$scope','$http', function($scope, $http) {
	$scope.submitSignup = function() {
		console.debug('Will submit', $scope.signup);
		 $http.post('bmauth/users', $scope.signup).success(function() {
			 console.debug("Success!");
		 }).error(function(data) {
			 console.debug("Error", data)
		 });
	}
}]);
