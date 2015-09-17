angular.module('bmauth.home')

.factory('signup', [ '$resource','$rootScope', function($resource, $rootScope) {
	return $resource($rootScope.authContext + 'bmauth/users');
}]);