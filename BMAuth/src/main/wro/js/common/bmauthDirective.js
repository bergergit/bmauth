angular.module("bmauth.main", [])

.directive("bmAuth", function() {
	var directive = {};
	//directive.restrict = 'AE';

	directive.scope = {
		appName: '@',
		signedInUri: '@'
	};

	//directive.templateUrl = "/bmauth/fragments/home/login.html";
	directive.template = '<div ng-include="contentUrl"></div>'

	directive.link = function(scope, elements, attr) {
		directive.init(scope);
	}
	
	directive.init = function(scope) {
		scope.contentUrl = '/bmauth/fragments/home/login.html;'
	}
	
	directive.controllerAs = 'vm';
		
	directive.controller = ['$scope','$location','auth', function ($scope, $location, auth) {
		
		var vm = this;
		
		/**
		 * Sign up form
		 */
		vm.signupForm = function() {
			$scope.contentUrl = "/bmauth/fragments/home/signup.html";
			//$location.path('/signup', false);
		}
		
		vm.authenticated = function() {
	        return auth.authenticated;
	    };
	    
	    /**
	     * Cancel. Takes to home again
	     */
		vm.cancel = function() {
			directive.init($scope);
		}
	  
	    /** 
	     * Internal login
	     */
	    vm.login = function() {
	        auth.authenticate(vm.credentials, function(authenticated) {
	            if (authenticated) {
	                console.log("Login succeeded");
	                vm.error = false;
	                $location.path($scope.signedInUri ? $scope.signedInUri : "/applications");
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
	}];
	
	return directive;
});