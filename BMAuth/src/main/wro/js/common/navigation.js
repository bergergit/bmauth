'use strict';

angular.module('bmauth.navigation', [])

/** Controller that manages navigation actions, related to the navbar */
.controller('NavigationCtrl', ['$scope','$location','$rootScope', function($scope, $location, $rootScope) {
	$scope.showNavBar = function() {
		// show navbar if in administration screens
		if ($location.path().match(/applications*|users*/)) {
			return true;
		}
		return false;
	}
}]);