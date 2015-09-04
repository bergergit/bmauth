'use strict';

angular.module('bmauth', [
  'ngRoute',
  'pascalprecht.translate',
  'datatables',
  'bmauth.home',
  'bmauth.applications',
  'bmauth.navigation',
  'bmauth.authentication',
  'bmauth.social.login',
  'bmauth.main'
 ])

.config(['$routeProvider', '$translateProvider', '$locationProvider', '$httpProvider', function($routeProvider, $translateProvider, $locationProvider, $httpProvider) {
	// routes configuration
	$routeProvider.when('/', { 
		  templateUrl: '/bmauth/fragments/home/home.html',
		  controller: 'LoginCtrl',
		  controllerAs: 'vm'
	}).when('/signup', { 
		  templateUrl: '/bmauth/fragments/home/signup.html',
		  controller: 'SignupCtrl',
		  controllerAs: 'vm'
	}).when('/applications', { 
		  templateUrl: '/bmauth/fragments/applications/list.html',
		  controller: 'ApplicationsListCtrl',
		  controllerAs: 'vm'
	}).when('/applications/:applicationId', { 
		  templateUrl: '/bmauthfragments/applications/edit.html',
		  controller: 'ApplicationsEditCtrl',
		  controllerAs: 'vm'
	}).otherwise({redirectTo: '/'});
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	// translation configuration
	$translateProvider.useSanitizeValueStrategy('escape');
	$translateProvider.useUrlLoader('messageBundle');
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// support natural routes
	$locationProvider.html5Mode(true);
}])

.run(['DTDefaultOptions','$translate','auth','facebook', function(DTDefaultOptions, $translate, auth, facebook) {
    DTDefaultOptions.setLanguageSource('fragments/lang/dtlang-' + $translate.use() + '.json');
    
    auth.init('/', 'bmauth/login', 'logout');
    facebook.init();
    
}]);  

