angular.module('bmauth.home')

.factory('Signup', [ '$resource', function($resource) {
	return $resource('bmauth/users');
}]);