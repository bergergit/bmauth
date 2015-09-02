angular.module('bmauth.authentication', [])

.factory('auth',['$http', '$location', function($http, $location) {

	var auth = {

		authenticated : false,

		loginPath : '/login',
		logoutPath : '/logout',
		homePath : '/',

		/**
		 * Will authenticate user using the internal login service, with basic authentication
		 */
		authenticate: function(credentials, callback) {

			var headers = credentials && credentials.username ? {
				authorization : "Basic " + btoa(credentials.username + ":" + credentials.password)
			} : {};
			
			console.debug('credentials', credentials.username, credentials.password);

			$http.get('bmauth/login', {
				headers : headers
			}).success(function(data) {
				console.debug('bmauth.authentication - data', data);
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
			$location.path(auth.loginPath);
			$http.post(auth.logoutPath, {});
			
		},

		init : function(homePath, loginPath, logoutPath) {
			auth.homePath = homePath;
			auth.loginPath = loginPath;
			auth.logoutPath = logoutPath;
		}

	};

	return auth;

}]); 