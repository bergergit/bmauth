angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap', 'ngResource','ui.bootstrap'])

/**
 * Application Edit controller
 * 
 * Call a factory application / ApplicationService.js
 */
.controller('ApplicationsEditCtrl', [ '$scope', 'applicationService', 'DTOptionsBuilder','DTColumnBuilder', '$routeParams', '$rootScope', '$location', '$filter', 'dtUtils','$modal', 
                                      function($scope, applicationService, DTOptionsBuilder, DTColumnBuilder,$routeParams, $rootScope, $location, $filter, dtUtils, $modal) {
	
	var vm = this;
	dtUtils.init(vm, $scope);
	var applicationsPromise = null;
	
	console.debug("id " + $routeParams.applicationId);
	
	// Default parameters
	vm.applicationField = new applicationService({"active": true, "testMode": "false", "mandatoryContract": "true"});
	if($routeParams.applicationId != 'new') {
		//vm.applicationField = applicationService.get({applicationId: $routeParams.applicationId});
		applicationsPromise = applicationService.get({applicationId: $routeParams.applicationId}).$promise;
		applicationsPromise.then(function (result) {
			vm.applicationField = result;
		});
	}
	
	// cancel - goes back to application list
	vm.cancel = function() {
		$location.path('/applications');
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
	
	// Renderer for the datatable
	var renderer = {
		active: function(data, type, full) {
			return data == '1' ? '<span class="glyphicon glyphicon-ok-circle"></span>' : '';
		},
		trash: function(data, type, full) {
			return '<button type="button" class="remove btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>';
		}
	}

	// Roles
	var dtOptionsBuilderRole = DTOptionsBuilder.newOptions();
	if (applicationsPromise) {
		dtOptionsBuilderRole = DTOptionsBuilder.fromFnPromise(function() {
			return applicationsPromise;
	    })
	}
    
	vm.dtOptionsRole = dtOptionsBuilderRole
		.withDataProp('rolesRest')
		.withOption('rowCallback', dtUtils.rowCallback)
	    .withBootstrap();
	
	vm.dtColumnsRole = [
	    DTColumnBuilder.newColumn('roleId').withTitle('ID'),
	    DTColumnBuilder.newColumn('roleName').withTitle('Role'),
		DTColumnBuilder.newColumn('roleId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
	
	vm.dtInstanceRole = {};
	
	// Execute Role modal
	vm.dtClickHandler = function(info, index) {
		$scope.translationData = {
			roleName: info.applicationName.rolesRest[index].roleName,
			roleId: info.applicationId.rolesRest[index].roleId
		}
		var modalInstance = $modal.open({
			 templateUrl: 'fragments/applications/editRoleModal.html',
			 scope: $scope
		});
		
//		modalInstance.result.then(function (result) {
//			vm.applicationService = new applicationService();
//	        vm.applicationService.$delete({applicationId: info.applicationId}, function() {
//	        	vm.appDeleted = true;
//	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
//	        });
//		});
				
//		};
    }

	// Execute delete
	vm.dtDeleteHandler = function(info) {
		$scope.translationData = {
			name: info.applicationName,
			id: info.applicationId
		}
		var modalInstance = $modal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});
		
		modalInstance.result.then(function (result) {
			vm.applicationService = new applicationService();
	        vm.applicationService.$delete({applicationId: info.applicationId}, function() {
	        	vm.appDeleted = true;
	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
	        });
		});
    }
	
	vm.dtDeleteHandler = function(info, index) {
	
		//console.debug(info);
		//console.debug(index);
		
		$scope.translationData = {
				name: info.roleName,
				id: info.roleId
			}
		var modalInstance = $modal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});

		modalInstance.result.then(function (result) {
			var idx = vm.applicationField.rolesRest.indexOf(info);
			if (idx >= 0) {
				vm.applicationField.rolesRest.splice(idx, 1);
				vm.dtInstanceRole.reloadData(null, true);	// reload the datatable
			}
		});
	};

	// Contract
	var dtOptionsBuilderContract = DTOptionsBuilder.newOptions();
	if (applicationsPromise) {
		dtOptionsBuilderContract = DTOptionsBuilder.fromFnPromise(function() {
			return applicationsPromise;
	    })
	}
	
	vm.dtOptionsContract = dtOptionsBuilderContract
	.withDataProp('onlineContractsRest')
    .withBootstrap();

	vm.dtColumnsContract = [
	    DTColumnBuilder.newColumn('contractVersion').withTitle('Version'),
	    /*
	     * The fields creationDate e etc was removed from rests
	     * DTColumnBuilder.newColumn('creationDate').withTitle('Creation Date').renderWith(function(data, type) {
		   return $filter('date')(data, 'dd/MM/yyyy @ HH:mm:ss');
		   })*/,
		DTColumnBuilder.newColumn('onlineContractId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
	 ];
	
	
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope', 'DTOptionsBuilder','DTColumnBuilder','applicationService', '$translate', '$location','$modal','dtUtils',
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, applicationService, $translate, $location, $modal, dtUtils) {
	
	var vm = this;
	dtUtils.init(vm, $scope);
	
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
	var promise = applicationService.query().$promise;
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		promise.then(function() {
			vm.ready = true;
		})
        return promise;
    })
    .withOption('rowCallback', dtUtils.rowCallback)
    .withBootstrap();
	
	// Datatable exposed Columns
	vm.dtColumns = [
        DTColumnBuilder.newColumn('applicationId').withTitle($translate('application.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('applicationName').withTitle($translate('application.form.label.name')),
        DTColumnBuilder.newColumn('active').withTitle($translate('application.form.label.active')).withClass('text-center').withOption('width', '100px').renderWith(renderer.active),
        DTColumnBuilder.newColumn('applicationId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
	
	vm.dtInstance = {};
	
	// Execute row click
	vm.dtClickHandler = function(info) {
		$location.path('applications/' + info.applicationId);
    }
	
	// Execute delete
	vm.dtDeleteHandler = function(info) {
		$scope.translationData = {
			name: info.applicationName,
			id: info.applicationId
		}
		var modalInstance = $modal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});
		
		modalInstance.result.then(function (result) {
			vm.applicationService = new applicationService();
	        vm.applicationService.$delete({applicationId: info.applicationId}, function() {
	        	vm.appDeleted = true;
	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
	        });
		});
    }
    
}]);