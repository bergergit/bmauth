'use strict';

angular.module('bmauth.signup.service', [ 'ngResource' ])

.factory('Signup', [ '$resource', function($resource) {
	return $resource('bmauth/users');
}]);