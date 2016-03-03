angular.module('bmauth.main')

.factory('userService', [ '$resource','$rootScope', function($resource, $rootScope) {
	return $resource($rootScope.authContext + 'bmauth/users/:userId');
}])

.factory('forgotMyPasswordService', [ '$resource','$rootScope', function($resource, $rootScope) {
	return $resource($rootScope.authContext + 'bmauth/token/generate_token');
}])

.factory('resetMyPasswordService', [ '$resource','$rootScope', function($resource, $rootScope) {
	return $resource($rootScope.authContext + 'bmauth/reset/reset_password');
}]);

