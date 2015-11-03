angular.module("bmauth.main", [])

.directive("bmAuth", function() {
	var directive = {};
	//directive.restrict = 'AE';
	
	directive.scope = {
		appName: '@',
		signedInUri: '@',
		showFacebook: '=',
		showGoogle: '=',
	};
	
	directive.controllerAs = 'vm';

	directive.template = '<div ng-include="contentUrl"></div>'

	directive.link = function(scope, elements, attr) {
		directive.init(scope);
	}
	
	directive.init = function($scope) { 
		$scope.contentUrl = directive.context + 'fragments/home/login.html';
		$scope.signupFormUrl = directive.context + 'fragments/home/signupForm.html';
		$scope.forgotPassoword = directive.context + 'fragments/home/forgotPassword.html';
	}
	
	/**
	 * Redirects to the signed in URI (if provided). Or else, redirects to BM Auth admin IF user
	 * has the right privileges.
	 */
	directive.signinRedirect = function($location, $scope, auth, vm) {
		if ($scope.signedInUri) {
			$location.path($scope.signedInUri);
		} else {
			if (_.indexOf(auth.data.roles, 'ROLE_BMAUTH-ADMIN') > -1) {
				$location.path('/applications');
			} else {
				console.debug('autorization error');
				vm.authorizationError = true;
			}
		}
	}
	
	directive.controller = ['$scope','$rootScope','$location','$http','auth','userService','Facebook','GooglePlus','formUtils','forgotMyPasswordService', 
	                        function ($scope, $rootScope, $location, $http, auth, userService, Facebook, GooglePlus, formUtils, forgotMyPasswordService) {

		var vm = this;
		vm.userCreated = false;
		directive.context = $rootScope.authContext;

		if ($scope.showFacebook === undefined) $scope.showFacebook = true;
		if ($scope.showGoogle === undefined) $scope.showGoogle = true;
		
	    vm.signup = new userService({
			"loginType": "3"
		});

	    vm.forgotMyPassword = new forgotMyPasswordService({
	    	"email" : "", 
	    	"appName": $scope.appName
	    });	    
	    
		/**
		 * Sign up form
		 */
		vm.signupForm = function() {
			$scope.contentUrl = directive.context + "fragments/home/signup.html";
		}

		vm.forgotMyPassowrd = function() {
			$scope.contentUrl = directive.context + "fragments/home/forgotPassword.html";
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
	                directive.signinRedirect($location, $scope, auth, vm);
	            } else {
	                console.log("Login failed");
	                vm.error = true;
	            }
	        })
	    };
	    
	    function authenticateFacebook($http, data) {
	    	data.appId = Facebook.appId;
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
			//console.debug('init option', Facebook.getInitOption('appId'));
			Facebook.login(function(response) {
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
		      });
		};
		
		/**
		 * Google login
		 */
		vm.googleLogin = function() {
			console.debug("Google login clicked!");
			//console.debug('Google', GooglePlus.prototype.login);
			GooglePlus.login().then(function (authResult) {
				console.debug("AuthResult", authResult);
	            authenticateGoogle($http, authResult);

	        }, function (err) {
	            console.log(err);
	        });
		};
		
		function authenticateGoogle($http, data) {
			var googleData = {accessToken: data.access_token, clientId: data.client_id}
			console.debug("Will post google data", googleData, directive.context + 'bmauth/users/google')
	    	$http.post(directive.context + 'bmauth/users/google', googleData)
	    		.then(function(response) {
		    		console.debug("Google User saved! We are good to go to signed in experience");
		    		directive.signinRedirect($location, $scope, auth, vm); 
				}, function(response) {
					console.debug("Error saving Google user");
					vm.socialLoginError = true;
				});
	    }
		
	    vm.logout = function() {
	      auth.clear();
	    };
	    
		// saves the user with a post, and redirects to signed in experience
		vm.submitSignup = function(form) {
			 console.debug('Will submit', vm.signup);
			 if (form.form.$valid) {
				 vm.signup.$save(function(response) {
					 // will redirect user after sign up if there is a redirectUri. Else, just display a 'user created' message 
					 if ($scope.signedInUri) {
						//console.debug("Success on save");
						directive.signinRedirect($location, $scope, auth, vm); 
					 } else {
						 vm.userCreated = true;
					 }
					 
				 }, function(error) {
					 console.debug("Error on save");
					 formUtils.handleFormErrors(error, 'formErrors', 'signup.label');
				 });
			 }
		}
		
		vm.submitForgotMyPasswordForm = function() {
			console.debug('Will submit forgotMyPassword', vm.forgotMyPassword);
			
			 vm.forgotMyPassword.$save(function(response) {
					console.debug("Forgot my password Success on save");
					vm.emailInvalido = false;
					vm.showError = false;
					vm.forgotMyPasswordCreated = true;
					directive.init($scope);
			 }, function(response) {
				 console.debug("Error on save token");
				 
				 if(response.status == 404){
					 vm.emailInvalido = true;
					 vm.showError = false;
				 } else {
					 vm.emailInvalido = false;
					 vm.showError = true;
					 vm.error = "Error: " + response.status + " - " + response.statusText;  
				 }
			 });
		}
		
	}];
	
	return directive;
});
