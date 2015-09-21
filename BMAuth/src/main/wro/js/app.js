'use strict';

angular.module('bmauth', [
  'ngRoute',
  'pascalprecht.translate',
  'datatables',
  'googleplus',
  'bmauth.home',
  'bmauth.applications',
  'bmauth.navigation',
  'bmauth.authentication',
  'bmauth.social.login',
  'bmauth.main'
 ]) 

.config(['$routeProvider', '$translateProvider', '$locationProvider', '$httpProvider','GooglePlusProvider', 
         function($routeProvider, $translateProvider, $locationProvider, $httpProvider, GooglePlusProvider) {
	// routes configuration
	$routeProvider.when('/', { 
		  templateUrl: 'fragments/home/home.html',
		  //controller: 'LoginCtrl',
		  controllerAs: 'vm'
	}).when('/applications', { 
		  templateUrl: 'fragments/applications/list.html',
		  controller: 'ApplicationsListCtrl',
		  controllerAs: 'vm'
	}).when('/applications/:applicationId', { 
		  templateUrl: 'fragments/applications/edit.html',
		  controller: 'ApplicationsEditCtrl',
		  controllerAs: 'vm' // replace the variable $scope for vm
	});//.otherwise({redirectTo: '/'});
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	// translation configuration
	$translateProvider.useSanitizeValueStrategy('escape');
    $translateProvider.translations('en', bmauth_translations.en);
	
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// support natural routes
	$locationProvider.html5Mode(true);
	
    GooglePlusProvider.init({
        clientId: '134948939899-bdres6i4dra21nn21e86d5nob40c0sl0.apps.googleusercontent.com',
        apiKey: 'AIzaSyBlaselNuEfCO6P71bsLxZH3ASNQzfu5CY',
        scopes: 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email'
     });
}])

.run(['DTDefaultOptions','$translate','$rootScope','auth','facebook','google', function(DTDefaultOptions, $translate, $rootScope, auth, facebook, google) {
    DTDefaultOptions.setLanguageSource('fragments/lang/dtlang-' + $translate.use() + '.json');
    $rootScope.authContext='';
    
    auth.init('/', 'user', 'logout');
    facebook.init();
    //google.init();
    
}]);  

