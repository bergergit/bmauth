angular.module('bmauth.home')

.factory('signup', [ '$resource', function($resource) {
	return $resource('bmauth/users');
}]);