angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap', 'ngResource','ui.bootstrap'])

/**
 * Application Edit controller
 * 
 * Call a factory application / ApplicationService.js
 */
.controller('ApplicationsEditCtrl', [ '$scope', 'applicationService', 'DTOptionsBuilder','DTColumnBuilder', '$routeParams', '$rootScope', '$location', '$filter', 'dtUtils','$uibModal', '$window',  
                                      function($scope, applicationService, DTOptionsBuilder, DTColumnBuilder,$routeParams, $rootScope, $location, $filter, dtUtils, $uibModal, $window) {
	
	var vm = this;
	dtUtils.init(vm, $scope);
	var applicationsPromise = null;
	
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
	vm.dtInstanceRole = {};
	
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
	    //DTColumnBuilder.newColumn('roleId').withTitle('ID'),
	    DTColumnBuilder.newColumn('roleName').withTitle('Role').renderWith(dtUtils.sanitize),
		DTColumnBuilder.newColumn('roleId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
	
	// Contract
	vm.dtInstanceContract = {};
	
	var dtOptionsBuilderContract = DTOptionsBuilder.newOptions();
	if (applicationsPromise) {
		dtOptionsBuilderContract = DTOptionsBuilder.fromFnPromise(function() {
			return applicationsPromise;
	    })
	}
	
	vm.dtOptionsContract = dtOptionsBuilderContract
	.withDataProp('onlineContractsRest')
	.withOption('rowCallback', dtUtils.rowCallback)
    .withBootstrap();

	vm.dtColumnsContract = [
	    //DTColumnBuilder.newColumn('onlineContractId').withTitle('ID'),
	    DTColumnBuilder.newColumn('contractVersion').withTitle('Version').renderWith(dtUtils.sanitize),
	    /*
	     * The fields creationDate e etc was removed from rests
	     * DTColumnBuilder.newColumn('creationDate').withTitle('Creation Date').renderWith(function(data, type) {
		   return $filter('date')(data, 'dd/MM/yyyy @ HH:mm:ss');
		   })*/,
		DTColumnBuilder.newColumn('onlineContractId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
	 ];
	
	vm.dtNewRole = function(){

		newRole = {
				roleName: "",
				roleId: "-1"
		}
		
		vm.dtClickHandler(newRole, null);
		
	}
	
	vm.dtNewOnlineContract = function(){

		newContract = {
						contractVersion: "",
						onlineContractId: "-1",
						description: "",
						languageContractsRest: []
		}
		
		vm.dtClickHandler(newContract, null);
		
	}

	vm.dtClickHandler = function(info, index) {
		
		if (info.roleId) {
			vm.data = {
				roleName: info.roleName,
				roleId: info.roleId
			}

			vm.idx = vm.applicationField.rolesRest.indexOf(info);
			
			$uibModal.open({
				templateUrl: 'fragments/applications/editRoleModal.html',
                scope: $scope
			}).result.then(function (result) {
					if (vm.idx >= 0) {
						vm.applicationField.rolesRest[vm.idx] = vm.data;
					} else {
						vm.applicationField.rolesRest.push(vm.data);
					}
					vm.dtInstanceRole.reloadData(null, true);	// reload the datatable
					vm.appRoleSaved = true;
					vm.appRoleDeleted = false;
					vm.appContractSaved = false;
					vm.appContractDeleted = false;
			});
			
		} else if (info.onlineContractId){
				vm.data = {
					contractVersion: info.contractVersion,
					onlineContractId: info.onlineContractId,
					description: info.description,
					languageContractsRest: []
				}
				
				angular.copy(info.languageContractsRest, vm.data.languageContractsRest);
				
				if (vm.data.languageContractsRest.length > 0){ 
					vm.data.languageContractsRest[0].active = true;
				}
				
				vm.idx = vm.applicationField.onlineContractsRest.indexOf(info);
				
				$uibModal.open({
					templateUrl: 'fragments/applications/editContractModal.html',
	                scope: $scope,
	                size: "lg"
				}).result.then(function (result) {
						if (vm.idx >= 0) {
							for (var i = 0; i < vm.data.languageContractsRest.length; i++) {
								delete vm.data.languageContractsRest[i]["active"];
							}
							vm.applicationField.onlineContractsRest[vm.idx] = vm.data;
						} else {
							for (var i = 0; i < vm.data.languageContractsRest.length; i++) {
								delete vm.data.languageContractsRest[i]["active"];
							}
							vm.applicationField.onlineContractsRest.push(vm.data);
						}
						vm.dtInstanceContract.reloadData(null, true);	// reload the datatable
						vm.appContractSaved = true;
						vm.appContractDeleted = false;
						vm.appRoleDeleted = false;
						vm.appRoleSaved = false;
				});
		}
	};
	
	
	vm.dtDeleteHandler = function(info, index) {
		
		if (info.roleId) {
			$scope.translationData = {
				name: info.roleName,
				id: info.roleId
			}
		} else if (info.onlineContractId){
				$scope.translationData = {
					name: info.contractVersion,
					id: info.onlineContractId
				}
		}
		
		var modalInstance = $uibModal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});

		modalInstance.result.then(function (result) {
			if (info.roleId){
				var idx = vm.applicationField.rolesRest.indexOf(info);
				if (idx >= 0) {
					vm.applicationField.rolesRest.splice(idx, 1);
					vm.dtInstanceRole.reloadData(null, true);	// reload the datatable
					vm.appRoleDeleted = true;
					vm.appRoleSaved = false;
					vm.appContractSaved = false;
					vm.appContractDeleted = false;
				}
			} else if (info.onlineContractId){
				var idx = vm.applicationField.onlineContractsRest.indexOf(info);
				if (idx >= 0) {
					vm.applicationField.onlineContractsRest.splice(idx, 1);
					vm.dtInstanceContract.reloadData(null, true);	// reload the datatable
					vm.appContractDeleted = true;
					vm.appContractSaved = false;
					vm.appRoleDeleted = false;
					vm.appRoleSaved = false;
				}
			}
		});
	};
	
	vm.dtAddNewLanguageContract = function() {
		
		newData = {
		          "htmlContract": "",
		          "language": "New"
		};

		vm.data.languageContractsRest.push(newData);
		var idx = vm.data.languageContractsRest.length -1;
		vm.data.languageContractsRest[idx].active = true;
				
	};
	
	vm.removeLanguage = function(data){
		
		$scope.translationData = {
			name: data.language,
			id: data.languageContractId
		}
		
		var modalInstance = $uibModal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});

		modalInstance.result.then(function (result) {
			var idx = vm.data.languageContractsRest.indexOf(data);
			if (idx >= 0) {
				vm.data.languageContractsRest.splice(idx, 1);
			}
		});

	}; 

	
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope', 'DTOptionsBuilder','DTColumnBuilder','applicationService', '$translate', '$location','$uibModal','dtUtils',
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, applicationService, $translate, $location, $uibModal, dtUtils) {
	
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
        //DTColumnBuilder.newColumn('applicationId').withTitle($translate('application.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('applicationName').withTitle($translate('application.form.label.name')).renderWith(dtUtils.sanitize),
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
		var modalInstance = $uibModal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});
		
		modalInstance.result.then(function (result) {
			vm.applicationService = new applicationService();
	        vm.applicationService.$delete({applicationId: info.applicationId}, function() {
	        	vm.appDeleted = true;
	        	vm.appSaved = false;
	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
	        });
		});
    }
    
}]);
