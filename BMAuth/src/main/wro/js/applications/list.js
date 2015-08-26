'use strict';

angular.module('bmauth.applications.list', ['datatables', 'datatables.bootstrap'])

.controller('ApplicationsListCtrl', ['$scope','DTOptionsBuilder','DTColumnBuilder', function($scope, DTOptionsBuilder, DTColumnBuilder) {
	
	console.debug("in applications list controller");
	
	var vm = this;
	
	console.debug('DTOptionsBuilder', DTOptionsBuilder, DTColumnBuilder);
	
	$scope.dtOptions = DTOptionsBuilder
	    .fromSource('data.json')
	    // Add Bootstrap compatibility
	    .withBootstrap();
    
	$scope.dtColumns = [
        DTColumnBuilder.newColumn('id').withTitle('ID').withClass('text-danger'),
        DTColumnBuilder.newColumn('firstName').withTitle('First name'),
        DTColumnBuilder.newColumn('lastName').withTitle('Last name')
    ];
}]);