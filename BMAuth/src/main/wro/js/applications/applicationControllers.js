'use strict';

angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap'])

/**
 * Application Edit controller
 */
.controller('ApplicationsEditCtrl', ['$scope', function($scope) {
	
	console.debug("in applications controller");
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope','DTOptionsBuilder','DTColumnBuilder','Application', function($scope, DTOptionsBuilder, DTColumnBuilder, Application) {
	
	console.debug("in applications list controller");
	
	var vm = this;
	
	$scope.dtOptions = DTOptionsBuilder
	    .fromSource('data.json')
	    // Add Bootstrap compatibility
	    .withBootstrap();
    
	$scope.dtColumns = [
        DTColumnBuilder.newColumn('id').withTitle('ID').withClass('text-danger'),
        DTColumnBuilder.newColumn('firstName').withTitle('First name'),
        DTColumnBuilder.newColumn('lastName').withTitle('Last name')
    ];
	
	Application.query();
}]);