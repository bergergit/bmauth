'use strict';

angular.module('bmauth.login', [])

.controller('LoginCtrl', ['$scope', function($scope) {
	
	console.debug("in login controller");
	
	$scope.submitLogin = function() {
		console.debug('I want to log iiiin');
	}
}])

.controller('SignupCtrl', ['$scope','$http','$location','Signup', function($scope, $http, $location, Signup) {
	$scope.signup = new Signup();
	$scope.submitSignup = function() {
		 console.debug('Will submit', $scope.signup);
		 $scope.signup.$save(function(response) {
			 console.debug("Success on save");
			 $location.path("/applications");
		 }, function() {
			 console.debug("Error on save");
		 });
		 
		 //$http.post('bmauth/users', $scope.signup).success(function() {
			 
	}
}]);
