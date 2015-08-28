angular.module('bmauth.home', [ 'ngResource' ])

.controller('LoginCtrl', ['$scope','Signup', function($scope, Signup) {
	
	var vm = this;
	
	$scope.submitLogin = function() {
		console.debug('I want to log iiiin');
	}
}])

.controller('SignupCtrl', ['$scope','$http','$location','Signup', function($scope, $http, $location, Signup) {
	var vm = this;
	
	vm.signup = new Signup();
	
	vm.submitSignup = function() {
		 console.debug('Will submit', $scope.signup);
		 $scope.signup.$save(function(response) {
			 //console.debug("Success on save");
			 $location.path("/applications");
		 }, function() {
			 console.debug("Error on save");
		 });
		 
		 //$http.post('bmauth/users', $scope.signup).success(function() {
			 
	}
}]);
