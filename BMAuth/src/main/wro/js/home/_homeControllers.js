angular.module('bmauth.home', [ 'ngResource' ])

.controller('LoginCtrl', ['auth','$location', function(auth, $location) {
	
	var vm = this;

	vm.credentials = {};

    vm.authenticated = function() {
        return auth.authenticated;
    };
  
    /** 
     * Internal login
     */
    vm.login = function() {
        auth.authenticate(vm.credentials, function(authenticated) {
            if (authenticated) {
                console.log("Login succeeded");
                vm.error = false;
                $location.path(auth.path ? auth.path : "/applications");
            } else {
                console.log("Login failed");
                vm.error = true;
            }
        })
    };
    

    /**
	 * Facebook login
	 */
	vm.facebookLogin = function() {
		FB.login(function(response) {
			console.debug("Facebook response", response);
			if (response.status === 'connected') {
				// Logged into your app and Facebook.
			} else if (response.status === 'not_authorized') {
				// The person is logged into Facebook, but not your app.
			} else {
				// The person is not logged into Facebook, so we're not sure if
				// they are logged into this app or not.
			}
		}, { scope : 'public_profile,email' });
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
	
	// saves the user with a post, and redirects to signed in experience
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
