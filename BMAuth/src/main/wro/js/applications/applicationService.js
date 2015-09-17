angular.module('bmauth.applications')

.factory('application', [ '$resource', function($resource) {
	return $resource('bmauth/applications');
}]);