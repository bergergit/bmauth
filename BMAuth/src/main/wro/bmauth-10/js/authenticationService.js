angular.module('bmauth.main')

.factory('auth',['$http', '$location','$rootScope','$cookies','$route','$routeParams', function($http, $location, $rootScope, $cookies, $route, $routeParams) {

	var auth = {

		authenticated : false,
		showFlash: false,

		loginPath : '/bmauth/user',
		logoutPath : '/bmauth/logout',
		homePath : '/',
		authenticatedPaths: '/applications*|/users*',
		
		data: {},

		/**
		 * Will authenticate user using the internal login service, with basic
		 * authentication
		 */
		authenticate: function(credentials, callback) {

			var headers = credentials && credentials.username ? {
				authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
			} : {};
			
			// console.debug('credentials', credentials.username, credentials.password);

			$http.get(auth.loginPath, {
				params: {
					"rememberMe": credentials && credentials.rememberMe ? credentials.rememberMe : null,
					"realm": credentials && credentials.realm ? credentials.realm : null,
					"appName": credentials && credentials.appName ? credentials.appName : null
				},
				headers: headers
			}).then(function(result) {
				auth.authenticationSuccess(result.data, callback);
			}, function() {
				auth.authenticated = false;
				callback && callback(false);
			});
		},
		
		authenticationSuccess: function(data, callback) {
			console.debug('bmauth.authentication - data', data);
			auth.data = data;
			auth.showFlash = false;
			
			// adding data in a session cookie
			
			
			var now = new Date();
	        now.setHours(now.getHours() + 2);
			//now.setSeconds(now.getSeconds() + 20);
			
	        $cookies.putObject('bmauth-data', data, {expires: now});
			
			
			if (data.id) {
				auth.authenticated = true;
			} else {
				auth.authenticated = false;
			}
			// $location.path(auth.homePath);
			callback && callback(auth.authenticated);
		},
		
		userEndpoint: function(callback) {
			$http.get(auth.loginPath).then(
				function(result) {
					auth.authenticationSuccess(result.data, callback);
				},
				function(error) {
					auth.authenticated = false;
					console.error("Error retrieving user endpoint", error);
				}
			);
		},

		clear : function(avoidPost) {
			var sessionStr = auth.showFlash ? "?session" : "";
			var reload = false;
			if (auth.authenticated && !avoidPost) {
				$http.post(auth.logoutPath, {});
				reload = true;
			}
			auth.authenticated = false;
			auth.data = null;
			$cookies.remove('bmauth-data');
			if (reload) {
				//TODO - uncomment
				document.location = auth.homePath + sessionStr;

				
				//$location.path(auth.homePath + sessionStr);
			}
		},
		
		/**
		 * Searches if the logged in user has this role
		 */
		hasRole : function(roles) {
			if (auth.isAnonymous()) return false;
			var hasRole = false;
			angular.forEach(roles, function(role) {
				hasRole = (_.indexOf(auth.data.roles, role.toUpperCase()) > -1) || hasRole;
			});
			return hasRole;
			
		},
		
		isAnonymous : function() {
			return (!auth.data || _.isEmpty(auth.data));
		},

		init : function(homePath, loginPath, logoutPath) {
			auth.path = null;
			auth.homePath = homePath;
			auth.loginPath = loginPath;
			auth.logoutPath = logoutPath;
			
			$rootScope.$on('$includeContentLoaded', function() {
			    auth.showFlash = false;
			});

			var reset = function() {
				if ($routeParams.reset || $location.search().reset) {
					auth.clear(true);
					$location.search('reset', null);
				}
			}
			
			$rootScope.$on('$routeChangeSuccess', function() {
				reset();
	        });
			$rootScope.$on('$stateChangeSuccess', function() {
				reset();
	        });
	        
			
			// try to recover data from session cookie
			var tempData = $cookies.getObject('bmauth-data');
			if (tempData) {
				auth.data = tempData;
				auth.authenticated = true;
			}

			// redirects user to home page if not authenticated
			var enter = function() {
				if ($location.path().match(auth.authenticatedPaths)) {
					auth.path = $location.path();
					if (!auth.authenticated) {
						$location.path(auth.homePath);
					}
				}
			}
		}
		
		

	};

	return auth;

}]); 