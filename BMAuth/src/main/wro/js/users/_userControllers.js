angular.module('bmauth.users', ['datatables', 'datatables.bootstrap', 'ngResource','ui.bootstrap'])

/**
 * Users Edit controller
 */
.controller('UsersEditCtrl', [ '$scope', '$compile', 'userService', 'applicationService', 'formUtils', 'DTOptionsBuilder','DTColumnBuilder', '$routeParams', '$rootScope', '$location','$translate',
                                      function($scope, $compile, userService, applicationService, formUtils, DTOptionsBuilder, DTColumnBuilder,$routeParams, $rootScope, $location, $translate) {
	var vm = this;
	var userPromise, applicationPromise;
	
	vm.signup = new userService({"loginType": "3"});
	if($routeParams.userId != 'new') {
		//vm.applicationField = applicationService.get({applicationId: $routeParams.applicationId});
		userPromise = userService.get({userId: $routeParams.userId}).$promise;
		userPromise.then(function (result) {
			vm.signup = result;
		});
	}
	
	// cancel - goes back to application list
	vm.cancel = function() {
		$location.path('/users');
	}
	
	// ng-submit
	vm.submitUserForm = function() {
		console.debug('Will submit', vm.signup);
		
		if ($scope.form.$valid) {
			vm.signup.$save(
				function(response) {
					$location.path($rootScope.authContext + '/users')
				 }, function(error) {
					console.debug("Error on save", error);
					formUtils.handleFormErrors(error, 'formErrors', 'signup.label');
				 }
			);
		}
	}
	
	// Application and roles Datatable
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		applicationPromise = applicationService.query().$promise;
		return applicationPromise;
    })
    //.withOption('rowCallback', dtUtils.rowCallback)
    .withOption('createdRow', createdRow)
    .withBootstrap();
	
	function createdRow(row, data, dataIndex) {
        // Recompiling so we can bind Angular directive to the DT
        $compile(angular.element(row).contents())($scope);
    }
	
	// Renderer for the datatable
	var renderer = {
		roles: function(data, type, full) {
			var column = '';
			angular.forEach(data, function(value, idx) {
				column += '<input type="checkbox" ng-model="vm.signup.simpleUserRoles[' + value.roleId + ']"/> ' + value.roleName + '<br/>';
			});
			return column;
		},
		app: function(data, type, full) {
			//return '<input type="checkbox" ng-model="vm.signup.simpleUserApplications[' + full.applicationId + ']"/> ' + data
		}
	}
	
	// Datatable exposed Columns
	vm.dtColumns = [
        DTColumnBuilder.newColumn('applicationId').withTitle($translate('application.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('applicationName').withTitle($translate('application.form.label.name')),
        DTColumnBuilder.newColumn('rolesRest').withTitle($translate('application.role.form.header')).renderWith(renderer.roles)
    ];
	
}])

/**
 * Users List controller
 */
.controller('UsersListCtrl', ['$scope','$rootScope', 'DTOptionsBuilder','DTColumnBuilder','userService', '$translate', '$location','$uibModal','dtUtils',
                                     function($scope, $rootScope, DTOptionsBuilder, DTColumnBuilder, userService, $translate, $location, $uibModal, dtUtils) {
	
	var vm = this;
	dtUtils.init(vm, $scope);
	
	// Renderer for the datatable
	var renderer = {
		active: function(data, type, full) {
			return data == '1' ? '<span class="glyphicon glyphicon-ok-circle"></span>' : '';
		},
		trash: function(data, type, full) {
			return '<button type="button" class="remove btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>';
		},
		loginType: function(data, type, full) {
			return $translate.instant('user.loginType.' + data);
		},
	}
	
	// Datatable exposed Options
	/*
	var promise = userService.query().$promise;
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		promise.then(function() {
			vm.ready = true;
		})
        return promise;
    })
    .withOption('rowCallback', dtUtils.rowCallback)
    .withBootstrap();
    */
	
	vm.dtOptions = DTOptionsBuilder.newOptions()
		.withOption('ajax', {
			url: $rootScope.authContext + 'bmauth/users/',
			type: 'GET',
			data: function(data) {
			   dtUtils.planify(data);  
			}
		})
		.withDataProp('data')
		.withOption('processing', true)
        .withOption('serverSide', true)
        .withOption('rowCallback', dtUtils.rowCallback)
        .withBootstrap();
		
	
	// Datatable exposed Columns
	vm.dtColumns = [
        //DTColumnBuilder.newColumn('userId').withTitle($translate('user.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('name').withTitle($translate('user.form.label.name')),
        DTColumnBuilder.newColumn('email').withTitle($translate('user.form.label.email')),
        DTColumnBuilder.newColumn('loginType').withTitle($translate('user.form.label.type')).renderWith(renderer.loginType),
        DTColumnBuilder.newColumn('active').withTitle($translate('user.form.label.active')).withClass('text-center').withOption('width', '100px').renderWith(renderer.active),
        DTColumnBuilder.newColumn('userId').withTitle('').withClass('text-center').withOption('width', '100px').renderWith(renderer.trash)
    ];
	
	vm.dtInstance = {};
	
	// Execute row click
	vm.dtClickHandler = function(info) {
		$location.path('users/' + info.userId);
    }
	
	// Execute delete
	vm.dtDeleteHandler = function(info) {
		$scope.translationData = {
			name: info.name,
			id: info.userId
		}
		var modalInstance = $uibModal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});
		
		modalInstance.result.then(function (result) {
			vm.userService = new userService();
	        vm.userService.$delete({userId: info.userId}, function() {
	        	vm.userDeleted = true;
	        	//promise = userService.query().$promise;
	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
	        });
		});
    }
    
}]);