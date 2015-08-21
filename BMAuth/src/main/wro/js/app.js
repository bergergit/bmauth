angular.module('megafunk', [
  'ngRoute',
  'pascalprecht.translate',
  'megafunk.home',
  'megafunk.navigation'
]).
config(['$routeProvider', '$translateProvider', '$locationProvider', function($routeProvider, $translateProvider, $locationProvider) {
	// routes configuration
	$routeProvider.when('/', { 
		  templateUrl: 'fragments/home/login.html',
		  controller: 'LoginCtrl'
	}).when('/signup', { 
		  templateUrl: 'fragments/home/signup.html'
	}).otherwise({redirectTo: '/'});
	
	// translation configuration
	$translateProvider.useSanitizeValueStrategy('escape');
	$translateProvider.useUrlLoader('messageBundle');
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// support natural routes
	$locationProvider.html5Mode(true);
}]);

