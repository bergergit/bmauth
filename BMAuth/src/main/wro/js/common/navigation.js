'use strict';

angular.module('bmauth.navigation', [])

/** Controller that manages navigation actions, related to the navbar */
.controller('NavigationCtrl', ['$location','auth', function($location, auth) {
	var vm = this;
	
	vm.showNavBar = function() {
		// show navbar if in administration screens
		if ($location.path().match(auth.authenticatedPaths)) {
			return true;
		}
		return false;
	}
	
	vm.logout = function() {
		auth.clear();
	}
}]);