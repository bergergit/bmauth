angular.module("bmauth.main", [])

.directive("bmAuth", function() {
	var directive = {};
	//directive.restrict = 'AE';

	directive.scope = {
		appName: '@',
		signedInUri: '@'
	};
	
	directive.controllerAs = 'vm';

	//directive.templateUrl = "/bmauth/fragments/home/login.html";
	directive.template = '<div ng-include="contentUrl"></div>'

	directive.link = function(scope, elements, attr) {
		directive.init(scope);
		
	}
	
	directive.init = function($scope) { 
		$scope.contentUrl = directive.context + 'fragments/home/login.html'
	}
	
	/**
	 * Redirects to the signed in URI (if provided). Or else, redirects to BM Auth admin IF user
	 * has the right privileges.
	 */
	directive.signinRedirect = function($location, $scope, auth, vm) {
		if ($scope.signedInUri) {
			$location.path($scope.signedInUri);
		} else {
			if (_.indexOf(auth.data.roles, 'ROLE_ADMIN') > -1) {
				$location.path('/applications');
			} else {
				console.debug('autorization error');
				vm.authorizationError = true;
			}
		}
	}
	
	directive.controller = ['$scope','$rootScope','$location','$http','auth','signup','GooglePlus', 
	                        function ($scope, $rootScope, $location, $http, auth, signup, GooglePlus) {
		console.debug("in controller...");
		var vm = this;
		vm.userCreated = false;
		//directive.context = $scope.context ? $scope.context : '';
		directive.context = $rootScope.authContext;
		
	    vm.signup = new signup({
			"loginType": "3"
		});
		
		/**
		 * Sign up form
		 */
		vm.signupForm = function() {
			$scope.contentUrl = directive.context + "fragments/home/signup.html";
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
	    	vm.error = false;
	        auth.authenticate(vm.credentials, function(authenticated) {
	            if (authenticated) {
	                console.log("Login succeeded");
	                vm.error = false;
	                //$location.path($scope.signedInUri ? $scope.signedInUri : "/applications");
	                directive.signinRedirect($location, $scope, auth, vm);
	            } else {
	                console.log("Login failed");
	                vm.error = true;
	            }
	        })
	    };
	    
	    function authenticateFacebook($http, data) {
	    	$http.post(directive.context + 'bmauth/users/facebook', data)
	    		.then(function(response) {
		    		console.debug("Facebook User saved! We are good to go to signed in experience");
		    		directive.signinRedirect($location, $scope, auth, vm); 
				}, function(response) {
					console.debug("Error saving facebook user");
					vm.socialLoginError = true;
				});
	    }

	    /**
		 * Facebook login
		 */
		vm.facebookLogin = function() {
			FB.login(function(response) {
				console.debug("Facebook response", response);
				if (response.status === 'connected') {
					// Logged into your app and Facebook.
					authenticateFacebook($http, response);
				} else if (response.status === 'not_authorized') {
					// The person is logged into Facebook, but not your app.
				} else {
					// The person is not logged into Facebook, so we're not sure if
					// they are logged into this app or not.
				}
			}, { scope : 'public_profile,email' });
		};
		
		/**
		 * Google login
		 */
		vm.googleLogin = function() {
			console.debug("Google login clicked!");
			GooglePlus.login().then(function (authResult) {
	            authenticateGoogle($http, authResult);

	            /*
	            GooglePlus.getUser().then(function (user) {
	                console.log(user);
	            });
	            */
	        }, function (err) {
	            console.log(err);
	        });
		};
		
		function authenticateGoogle($http, data) {
			console.debug("Will post google data", data, directive.context + 'bmauth/users/google')
	    	$http.post(directive.context + 'bmauth/users/google', data.access_token)
	    		.then(function(response) {
		    		console.debug("Google User saved! We are good to go to signed in experience");
		    		directive.signinRedirect($location, $scope, auth, vm); 
				}, function(response) {
					console.debug("Error saving Google user");
					vm.socialLoginError = true;
				});
	    }
		
		/*
		window.bmauth_gprender = function () {
			console.debug('render function');
			
			gapi.signin.render('googleSignup', {
		      'callback': signinCallback,
		      'clientid': '134948939899-bdres6i4dra21nn21e86d5nob40c0sl0.apps.googleusercontent.com',
		      'cookiepolicy': 'single_host_origin',
		      'requestvisibleactions': 'http://schemas.google.com/AddActivity',
		      'scope': 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email'
		    });
			
			function signinCallback(authResult) {
				console.debug('authResult', authResult);
			}
		}
		*/
		
	    vm.logout = function() {
	      auth.clear();
	    };
	    
		// saves the user with a post, and redirects to signed in experience
		vm.submitSignup = function() {
			 console.debug('Will submit', vm.signup);
			 vm.signup.$save(function(response) {
				 // will redirect user after sign up if there is a redirectUri. Else, just display a 'user created' message 
				 if ($scope.signedInUri) {
					//console.debug("Success on save");
					directive.signinRedirect($location, $scope, auth, vm); 
				 } else {
					 vm.userCreated = true;
				 }
				 
			 }, function() {
				 console.debug("Error on save");
			 });
		}
	}];
	
	return directive;
});