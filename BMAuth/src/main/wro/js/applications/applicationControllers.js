'use strict';

angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap'])

/**
 * Application Edit controller
 */
.controller('ApplicationsEditCtrl', ['$scope', function($scope) {
	
	console.debug("in applications controller");
	
	$scope.application = {"active": true, "testMode": "0"};
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope','DTOptionsBuilder','DTColumnBuilder','Application', '$translate', 
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, Application, $translate) {
	
	console.debug("in applications list controller");
	
	// Renderer for the datatable
	var renderer = {
		active: function(data, type, full) {
			return data == '1' ? '<span class="glyphicon glyphicon-ok-circle"></span>' : '';
		},
		trash: function(data, type, full) {
			return '<button type="button" class="remove btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>';
		}
	}
	
	$scope.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
        return Application.query().$promise;
    })
    //.withPaginationType('full_numbers')
    .withBootstrap();
	
	$scope.dtColumns = [
        DTColumnBuilder.newColumn('applicationId').withTitle('ID').withOption('width', '100px'),
        DTColumnBuilder.newColumn('applicationName').withTitle($translate('application.form.label.name')),
        DTColumnBuilder.newColumn('active').withTitle('Ativo').withClass('text-center').withOption('width', '100px').renderWith(renderer.active),
        DTColumnBuilder.newColumn('applicationId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
    
}]);