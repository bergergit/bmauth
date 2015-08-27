'use strict';

angular.module('bmauth.applications.service', [ 'ngResource' ])

.factory('Application', [ '$resource', function($resource) {
	return $resource('bmauth/applications');
}]);