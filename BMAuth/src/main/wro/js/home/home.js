'use strict';

angular.module('megafunk.home', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
	  templateUrl: 'fragments/home/login.html',
	  controller: 'LoginCtrl'
  });
}])

.controller('LoginCtrl', [function() {
	console.debug("in login controller");
}])
;