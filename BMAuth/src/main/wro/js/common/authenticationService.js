angular.module('bmauth.authentication', ['ngCookies'])

.factory('auth',['$http', '$location','$rootScope','$cookies', function($http, $location, $rootScope, $cookies) {

	var auth = {

		authenticated : false,

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
			
			// console.debug('credentials', credentials.username,
			// credentials.password);

			$http.get(auth.loginPath, {
				params: {
					"rememberMe": credentials.rememberMe,
					"realm": credentials.realm
				},
				headers: headers
			}).success(function(data) {
				console.debug('bmauth.authentication - data', data);
				auth.data = data;
				
				// adding data in a session cookie
				$cookies.putObject('bmauth-data', data);
				
				if (data.name) {
					auth.authenticated = true;
				} else {
					auth.authenticated = false;
				}
				// $location.path(auth.homePath);
				callback && callback(auth.authenticated);
			}).error(function() {
				auth.authenticated = false;
				callback && callback(false);
			});

		},

		clear : function() {
			auth.authenticated = false;
			auth.data = null;
			$cookies.remove('bmauth-data');
			$location.path(auth.homePath);
			$http.post(auth.logoutPath, {});
			
		},
		
		/**
		 * Searches if the logged in user has this role
		 */
		hasRole : function(role) {
			if (auth.isAnonymoys()) return false;
			return (_.indexOf(auth.data.roles, role.toUpperCase()) > -1);
		},
		
		isAnonymous : function() {
			return (!auth.data || _.isEmpty(auth.data));
		},

		init : function(homePath, loginPath, logoutPath) {
			auth.path = null;
			auth.homePath = homePath;
			auth.loginPath = loginPath;
			auth.logoutPath = logoutPath;
			
			// auth.authenticate();
			
			$rootScope.$on('$routeChangeStart', function() {
				// enter();
	        });
			
			// try to recover data from session cookie
			var tempData = $cookies.getObject('bmauth-data');
			if (tempData) {
				auth.data = tempData;
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