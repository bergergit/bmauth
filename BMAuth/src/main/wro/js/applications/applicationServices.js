'use strict';

angular.module('bmauth.applications.service', [ 'ngResource' ])

.factory('Application', [ '$resource', function($resource) {
	var factory = { };
	
	factory.query = function() { 
		return $resource('bmauth/applications').query();
    };

	return factory;
}]);