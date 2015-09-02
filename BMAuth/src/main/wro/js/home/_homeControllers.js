angular.module('bmauth.home', [ 'ngResource' ])

.controller('LoginCtrl', ['auth','$location', function(auth, $location) {
	
	var vm = this;

	vm.credentials = {};

    vm.authenticated = function() {
        return auth.authenticated;
    };
  
    vm.login = function() {
        auth.authenticate(vm.credentials, function(authenticated) {
            if (authenticated) {
                console.log("Login succeeded");
                vm.error = false;
                $location.path("/applications");
            } else {
                console.log("Login failed");
                vm.error = true;
            }
        })
    };

    vm.logout = function() {
      auth.clear();
    };

}])

.controller('SignupCtrl', ['$http','$location','signup', function($http, $location, signup) {
	var vm = this;
	
	vm.signup = new signup({
		"loginType": "3"
	});
	
	vm.submitSignup = function() {
		 console.debug('Will submit', vm.signup);
		 vm.signup.$save(function(response) {
			 //console.debug("Success on save");
			 $location.path("/applications");
		 }, function() {
			 console.debug("Error on save");
		 });
		 
		 //$http.post('bmauth/users', $scope.signup).success(function() {
			 
	}
}]);
