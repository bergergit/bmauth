'use strict';
 
angular.module('bmauth', [
  'ngRoute',
  'pascalprecht.translate',
  'datatables',
  'bmauth.main',
  'bmauth.applications',
  'bmauth.users',
  'bmauth.navigation',
  'ngSanitize',
  'ui.router'
 ]) 

.config(['$routeProvider', '$translateProvider', '$locationProvider', '$httpProvider','bmauthFormErrorsConfigProvider', 
         function($routeProvider, $translateProvider, $locationProvider, $httpProvider, bmauthFormErrorsConfigProvider) {
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
	}).when('/reset/:token/:userid', { 
		  templateUrl: 'fragments/home/resetPassword.html',
		  controllerAs: 'vm' 
	}).when('/users/signingcontract', { 
		  templateUrl: 'fragments/home/signingContract.html',
		  controllerAs: 'vm' 
	}).otherwise({redirectTo: '/'});
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

	// translation configuration
	//$translateProvider.useSanitizeValueStrategy('escape');
	$translateProvider.useSanitizeValueStrategy('escapeParameters');
	$translateProvider.translations('en', bmauth_translations_admin.en);
	$translateProvider.translations('en', bmauth_translations.en);
    
	
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// enabled front end form validation
	bmauthFormErrorsConfigProvider.enabled = true;
	
	// support natural routes
	$locationProvider.html5Mode(true);

}])

.run(['DTDefaultOptions','$translate','$rootScope','auth', function(DTDefaultOptions, $translate, $rootScope, auth) {
    DTDefaultOptions.setLanguageSource('fragments/lang/dtlang-pt_br.json');
    $rootScope.authContext='';
    
    auth.init('/', 'user', 'logout');
}]);  

