'use strict';

angular.module('bmauth', [
  'ngRoute',
  'pascalprecht.translate',
  'datatables',
  'googleplus',
  'facebook',
  'bmauth.home',
  'bmauth.applications',
  'bmauth.users',
  'bmauth.navigation',
  'bmauth.authentication',
  'bmauth.main',
  'bmauth.common',
  'ngSanitize'
 ]) 

.config(['$routeProvider', '$translateProvider', '$locationProvider', '$httpProvider', 
         function($routeProvider, $translateProvider, $locationProvider, $httpProvider) {
	// routes configuration
	$routeProvider.when('/', { 
		  templateUrl: 'fragments/home/home.html',
		  controllerAs: 'vm'
	}).when('/applications', { 
		  templateUrl: 'fragments/applications/list.html',
		  controller: 'ApplicationsListCtrl',
		  controllerAs: 'vm'
	}).when('/applications/:applicationId', { 
		  templateUrl: 'fragments/applications/edit.html',
		  controller: 'ApplicationsEditCtrl',
		  controllerAs: 'vm' // replace the variable $scope for "this"
	}).when('/users', { 
		  templateUrl: 'fragments/users/list.html',
		  controller: 'UsersListCtrl',
		  controllerAs: 'vm' 
	}).when('/users/:userId', { 
		  templateUrl: 'fragments/users/edit.html',
		  controller: 'UsersEditCtrl',
		  controllerAs: 'vm'
	}).otherwise({redirectTo: '/'});
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	// translation configuration
	//$translateProvider.useSanitizeValueStrategy('escape');
	$translateProvider.useSanitizeValueStrategy('escapeParameters');
    $translateProvider.translations('en', bmauth_translations.en);
	
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// support natural routes
	$locationProvider.html5Mode(true);

}])

.run(['DTDefaultOptions','$translate','$rootScope','auth', function(DTDefaultOptions, $translate, $rootScope, auth) {
    DTDefaultOptions.setLanguageSource('fragments/lang/dtlang-' + $translate.use() + '.json');
    $rootScope.authContext='';
    
    auth.init('/', 'user', 'logout');
}]);  

