angular.module('megafunk', [
  'ngRoute',
  'pascalprecht.translate',
  'megafunk.home',
  'megafunk.navigation'
]).
config(['$routeProvider', '$translateProvider', function($routeProvider, $translateProvider) {
  $routeProvider.otherwise({redirectTo: '/'});
  $translateProvider.useSanitizeValueStrategy('escape');
  $translateProvider.useUrlLoader('messageBundle');
  $translateProvider.preferredLanguage('en');
  $translateProvider.fallbackLanguage('en');
}]);

