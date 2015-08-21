'use strict';

angular.module('bmauth.navigation', [])

/** Controller that manages navigation actions, related to the navbar */
.controller('NavigationCtrl', ['$scope','$location', function($scope, $location) {
	$scope.setNavbarClass = function() {
		// makes the navbar transparent if we are in home or login screen
		if (_.contains(['/','/login'], $location.path())) {
			return 'navbar-transparent';
		}
		return '';
	}
}]);