angular.module('bmauth.main', ['ngCookies','ngRoute','googleplus', 'facebook','ngRoute'])

.directive('bmAuth', ['$location', function($location) {
	var directive = {};
	//directive.restrict = 'AE';
	
	directive.scope = {
		appName: '@',
		realm: '@',
		signedInUri: '@',
		showFacebook: '=',
		showGoogle: '=',
		showSignUp: '='
	};
	
	directive.controllerAs = 'vm';

	directive.template = '<div ng-include="contentUrl"></div>'

	directive.link = function(scope, location, elements, attr) {
		directive.init(scope, $location);
	}
	
	/**
	 * Initializes base template URLs and set the 1st page.
	 */
	directive.init = function($scope, $location) { 
		$scope.loginUrl = directive.context + 'bmauth-10/login.html';
		$scope.signupUrl = directive.context + 'bmauth-10/signup.html';
		$scope.signupFormUrl = directive.context + 'bmauth-10/signupForm.html';
		$scope.forgotPassword = directive.context + 'bmauth-10/forgotPassword.html';
		$scope.resetMyPassword = directive.context + 'bmauth-10/resetPasswordForm.html';
		$scope.signingContract = directive.context + 'bmauth-10/signingContractForm.html';

		
		
		if ($location.path().match("/reset/*")) {
			$scope.contentUrl = $scope.resetMyPassword;
		} else {
			// $scope.contentUrl = directive.context + 'fragments/directive/signingContractForm.html';
			$scope.contentUrl = $scope.loginUrl;
		}

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
				//console.debug('autorization error');
				vm.authorizationError = true;
			}
		}
	}
	
	directive.controller = ['$scope','$rootScope','$location','$http','auth','userService','Facebook','GooglePlus','formUtils','forgotMyPasswordService', 'resetMyPasswordService', '$routeParams','$translate','$stateParams',
	                        function ($scope, $rootScope, $location, $http, auth, userService, Facebook, GooglePlus, formUtils, forgotMyPasswordService, resetMyPasswordService, $routeParams, $translate, $stateParams) {

		var vm = this;
		vm.userCreated = false;
		vm.conflict = false;
		directive.context = $rootScope.authContext;
		

		if ($scope.showFacebook === undefined) $scope.showFacebook = true;
		if ($scope.showGoogle === undefined) $scope.showGoogle = true;
		if ($scope.showSignUp === undefined) $scope.showSignUp = true;
		
	    vm.signup = new userService({
			"loginType": "3"
		});

	    vm.forgotMyPassword = new forgotMyPasswordService({
	    	"email" : "", 
	    	"appName": $scope.appName
	    });	    

	    vm.resetMyPassword = new resetMyPasswordService({
	    	"password" : "", 
	    	"password2": "",
	    	"token": $routeParams.token || $stateParams.token,
	    	"userId": $routeParams.userid || $stateParams.userid
	    });	    
	    
	    
		/**
		 * Sign up form
		 */
		vm.signupForm = function() {
			$scope.contentUrl = $scope.signupUrl;
		}

		vm.forgotMyPasswordCall = function() {
			$scope.contentUrl = $scope.forgotPassword;
		}

		vm.authenticated = function() {
	        return auth.authenticated;
	    };
	    
	    /**
	     * Cancel. Takes to home again
	     */
		vm.cancel = function() {
			if ($location.path().substring(0, 7) == "/reset/") {
				$location.path($scope.loginUrl);
			} else {
				directive.init($scope, $location);
			}
		}
	  
	    /** 
	     * Internal login
	     */
	    vm.login = function() {
	    	vm.error = false;
	    	vm.credentials.realm = $scope.realm;
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

	    /**
		 * Facebook login
		 */
		vm.facebookLogin = function() {
			//console.debug('init option', Facebook.getInitOption('appId'));
			Facebook.login(function(response) {
				//console.debug("Facebook response", response);
				if (response.status === 'connected') {
					// Logged into your app and Facebook.
					authenticateFacebook($http, response);
				} else if (response.status === 'not_authorized') {
					// The person is logged into Facebook, but not your app.
				} else {
					// The person is not logged into Facebook, so we're not sure if
					// they are logged into this app or not.
				}
		    }, {scope: 'public_profile,email'});
		};
		
		function authenticateFacebook($http, data) {
	    	data.appId = Facebook.appId;
	    	data.realm = $scope.realm;
	    	$http.post(directive.context + 'bmauth/users/facebook', data, {params: {realm: $scope.realm}})
	    		.then(function(response) {
		    		//console.debug("Facebook User saved! We are good to go to signed in experience");
		    		auth.userEndpoint(directive.signinRedirect($location, $scope, auth, vm)); 
				}, function(response) {
					//console.debug("Error saving facebook user");
					vm.socialLoginError = true;
				});
	    }
		
		/**
		 * Google login
		 */
		vm.googleLogin = function() {
			//console.debug('Google', GooglePlus.prototype.login);
			GooglePlus.login().then(function (authResult) {
				//console.debug("AuthResult", authResult);
	            authenticateGoogle($http, authResult);

	        }, function (err) {
	            console.log(err);
	        });
		};
		
		function authenticateGoogle($http, data) {
			var googleData = {accessToken: data.access_token, clientId: data.client_id, realm: $scope.realm }
			//console.debug("Will post google data", googleData, directive.context + 'bmauth/users/google')
	    	$http.post(directive.context + 'bmauth/users/google', googleData, {params: {realm: $scope.realm}})
	    		.then(function(response) {
		    		//console.debug("Google User saved! We are good to go to signed in experience");
	    			auth.userEndpoint(directive.signinRedirect($location, $scope, auth, vm)); 
				}, function(response) {
					//console.debug("Error saving Google user");
					vm.socialLoginError = true;
				});
	    }
		
	    vm.logout = function() {
	      auth.clear();
	    };
	    
		// saves the user with a post, and redirects to signed in experience
		vm.submitSignup = function(screen) {
			 //console.debug('Will submit', vm.signup);
			 if (screen.form.$valid) {
				 vm.signup.realm = $scope.realm; 
				 vm.signup.$save({realm: $scope.realm}, function(response) {
					 // will redirect user after sign up if there is a redirectUri. Else, just display a 'user created' message 
					 if ($scope.signedInUri) {
						//console.debug("Success on save");
						auth.authenticate(vm.signup, directive.signinRedirect($location, $scope, auth, vm));
					 } else {
						 vm.userCreated = true;
					 }

					 //console.debug('return after user created', $scope.signedInUri);
					 
				 }, function(error) {
					 //console.debug("Error on save", error);
					 if (error.status === 409) { 
						 vm.conflict = true;
					 } else {
					 	formUtils.handleFormErrors(error, 'formErrors', 'signup.label');
					 }
				 });
			 }
		}
		
		vm.submitForgotMyPasswordForm = function() {
			//console.debug('Will submit forgotMyPassword', vm.forgotMyPassword);
			
			 vm.forgotMyPassword.$save(function(response) {
					//console.debug("Forgot my password Success on save");
					vm.emailInvalido = false;
					vm.showError = false;
					vm.forgotMyPasswordCreated = true;
					directive.init($scope, $location);
			 }, function(response) {
				 //console.debug("Error on save token");
				 
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

		vm.submitResetMyPasswordForm = function(screen) {
			//console.debug('Will submit resetMyPassword', vm.resetMyPassword);
			if (screen.form.$valid) {
				 vm.resetMyPassword.$save(function(response) {
						//console.debug("reset my password Success on save");
						vm.showError = false;
						vm.resetOK = true;
						//$location.path(auth.homePath);
						$scope.contentUrl = $scope.loginUrl;
				 }, function(response) {
					 console.debug("Error on reset password", response);
					 vm.showError = true;
					 vm.error = "Error: " + response.status + " - " + response.statusText;  
	
					 if(response.status == 404){
						 vm.showError = true;
						 vm.error = $translate.instant('resetPassword.message.error.400');  
					 } else {
						 vm.showError = true;
						 vm.error = "Error: " + response.status + " - " + response.statusText;  
					 }
				 
				 });
			} else {
				//console.debug("Forms is not valid")
			}
		}
		
	}];
	
	return directive;
}]);
