angular.module('bmauth.authentication', [])

.factory('auth',['$http', '$location','$rootScope', function($http, $location, $rootScope) {

	var auth = {

		authenticated : false,

		loginPath : '/bmauth/user',
		logoutPath : '/bmauth/logout',
		homePath : '/',
		authenticatedPaths: '/applications*|/users*',
		
		data: {},

		/**
		 * Will authenticate user using the internal login service, with basic authentication
		 */
		authenticate: function(credentials, callback) {

			var headers = credentials && credentials.username ? {
				authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
			} : {};
			
			//console.debug('credentials', credentials.username, credentials.password);

			$http.get(auth.loginPath + '?rememberMe=' + credentials.rememberMe, {
				headers : headers
			}).success(function(data) {
				console.debug('bmauth.authentication - data', data);
				auth.data = data;
				if (data.name) {
					auth.authenticated = true;
				} else {
					auth.authenticated = false;
				}
				//$location.path(auth.homePath);
				callback && callback(auth.authenticated);
			}).error(function() {
				auth.authenticated = false;
				callback && callback(false);
			});

		},

		clear : function() {
			auth.authenticated = false;
			$location.path(auth.homePath);
			$http.post(auth.logoutPath, {});
			
		},

		init : function(homePath, loginPath, logoutPath) {
			auth.path = null;
			auth.homePath = homePath;
			auth.loginPath = loginPath;
			auth.logoutPath = logoutPath;
			
			//auth.authenticate();
			
			$rootScope.$on('$routeChangeStart', function() {
				//enter();
	        });

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