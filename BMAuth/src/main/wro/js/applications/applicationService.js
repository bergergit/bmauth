angular.module('bmauth.applications')

.factory('applicationService', [ '$resource','$rootScope', function($resource, $rootScope) {
	return $resource($rootScope.authContext + 'bmauth/applications/:applicationId');
}]);