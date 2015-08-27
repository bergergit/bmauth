angular.module('bmauth', [
  'ngRoute',
  'pascalprecht.translate',
  'datatables',
  'bmauth.login',
  'bmauth.applications',
  'bmauth.applications.service',
  'bmauth.navigation'
])

.config(['$routeProvider', '$translateProvider', '$locationProvider', function($routeProvider, $translateProvider, $locationProvider) {
	// routes configuration
	$routeProvider.when('/', { 
		  templateUrl: 'fragments/home/login.html',
		  controller: 'LoginCtrl'
	}).when('/signup', { 
		  templateUrl: 'fragments/home/signup.html',
		  controller: 'SignupCtrl'
	}).when('/applications', { 
		  templateUrl: 'fragments/applications/list.html',
		  controller: 'ApplicationsListCtrl'
	}).when('/applications/:applicationId', { 
		  templateUrl: 'fragments/applications/edit.html',
		  controller: 'ApplicationsEditCtrl'
	})//.otherwise({redirectTo: '/'});
	
	// translation configuration
	$translateProvider.useSanitizeValueStrategy('escape');
	$translateProvider.useUrlLoader('messageBundle');
	$translateProvider.preferredLanguage('en');
	$translateProvider.fallbackLanguage('en');
	
	// support natural routes
	$locationProvider.html5Mode(true);
}])

.run(['DTDefaultOptions','$translate', function(DTDefaultOptions, $translate) {
	console.debug('$translate.use()', $translate.use());

    DTDefaultOptions.setLanguageSource('fragments/lang/dtlang-' + $translate.use() + '.json');
}]);
