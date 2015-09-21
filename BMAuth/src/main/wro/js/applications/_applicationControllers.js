angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap', 'ngResource'])

/**
 * Application Edit controller
 * 
 * Call a factory application / ApplicationService.js
 */
.controller('ApplicationsEditCtrl', [ 'applicationService', 'DTOptionsBuilder','DTColumnBuilder', '$routeParams', '$rootScope', '$location', 
                                      function(applicationService, DTOptionsBuilder, DTColumnBuilder,$routeParams, $rootScope, $location) {
	
	var vm = this;
	var applicationsPromise = null;
	
	console.debug("id " + $routeParams.applicationId);
	
	vm.applicationField = new applicationService({"active": true, "testMode": "false", "mandatoryContract": "true"});
	if($routeParams.applicationId != 'new') {
		//vm.applicationField = applicationService.get({applicationId: $routeParams.applicationId});
		applicationsPromise = vm.applicationField = applicationService.get({applicationId: $routeParams.applicationId}).$promise;
		applicationsPromise.then(function (result) {
			vm.applicationField = result;
		});
	} 
	
	// ng-submit
	vm.submitApplicationForm = function(){
		console.debug('Will submit', vm.applicationField);
		vm.applicationField.$save(
			function(response) {
				console.debug("Saved and redirecting");
				$location.path($rootScope.authContext + '/applications')
				
			 }, function() {
				 console.debug("Error on save");
			 }
		);
	}
	
	var dtOptionsBuilder = DTOptionsBuilder.newOptions();
	if (applicationsPromise) {
		dtOptionsBuilder = DTOptionsBuilder.fromFnPromise(function() {
			return applicationsPromise;
	    })
	}
    
	vm.dtOptions = dtOptionsBuilder
		.withDataProp('rolesRest')
	    .withBootstrap();
	
	vm.dtColumns = [
	    DTColumnBuilder.newColumn('roleId').withTitle('ID'),
	    DTColumnBuilder.newColumn('roleName').withTitle('Role')
    ]
	
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope', 'DTOptionsBuilder','DTColumnBuilder','applicationService', '$translate', '$location',
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, applicationService, $translate, $location) {
	
	var vm = this;
	
	// Renderer for the datatable
	var renderer = {
		active: function(data, type, full) {
			return data == '1' ? '<span class="glyphicon glyphicon-ok-circle"></span>' : '';
		},
		trash: function(data, type, full) {
			return '<button type="button" class="remove btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>';
		}
	}
	
	// Datatable exposed Options
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var promise = applicationService.query().$promise;
		promise.then(function() {
			vm.ready = true;
		})
        return promise;
    })
    .withOption('rowCallback', rowCallback)
    .withBootstrap();
	
	// Datatable exposed Columns
	vm.dtColumns = [
        DTColumnBuilder.newColumn('applicationId').withTitle($translate('application.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('applicationName').withTitle($translate('application.form.label.name')),
        DTColumnBuilder.newColumn('active').withTitle($translate('application.form.label.active')).withClass('text-center').withOption('width', '100px').renderWith(renderer.active),
        DTColumnBuilder.newColumn('applicationId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
	
	// Capturing row click
	vm.dtClickHandler = function(info) {
        //vm.message = info.id + ' - ' + info.firstName;
		$location.path('applications/' + info.applicationId);
    }
    function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        // Unbind first in order to avoid any duplicate handler (see https://github.com/l-lin/angular-datatables/issues/87)
        $('td', nRow).unbind('click');
        $('td', nRow).bind('click', function() {
            $scope.$apply(function() {
                vm.dtClickHandler(aData);
            });
        });
        return nRow;
    }
    
}]);