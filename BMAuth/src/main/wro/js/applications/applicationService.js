angular.module('bmauth.applications')

.factory('Application', [ '$resource', function($resource) {
	return $resource('bmauth/applications');
}]);