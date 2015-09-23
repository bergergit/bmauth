angular.module('bmauth.users', ['datatables', 'datatables.bootstrap', 'ngResource','ui.bootstrap'])

/**
 * Users Edit controller
 */
.controller('UsersEditCtrl', [ 'applicationService', 'DTOptionsBuilder','DTColumnBuilder', '$routeParams', '$rootScope', '$location',
                                      function(applicationService, DTOptionsBuilder, DTColumnBuilder,$routeParams, $rootScope, $location) {
	
	var vm = this;
	var applicationsPromise = null;
	
	console.debug("id " + $routeParams.applicationId);
	
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
 * Users List controller
 */
.controller('UsersListCtrl', ['$scope', 'DTOptionsBuilder','DTColumnBuilder','userService', '$translate', '$location','$modal','dtUtils',
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, userService, $translate, $location, $modal, dtUtils) {
	
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
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var promise = userService.query().$promise;
		promise.then(function() {
			vm.ready = true;
		})
        return promise;
    })
    .withOption('rowCallback', dtUtils.rowCallback)
    .withBootstrap();
	
	// Datatable exposed Columns
	vm.dtColumns = [
        DTColumnBuilder.newColumn('userId').withTitle($translate('user.form.label.id')).withOption('width', '100px'),
        DTColumnBuilder.newColumn('name').withTitle($translate('user.form.label.name')),
        DTColumnBuilder.newColumn('email').withTitle($translate('user.form.label.email')),
        DTColumnBuilder.newColumn('loginTypeName').withTitle($translate('user.form.label.type')),
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
		var modalInstance = $modal.open({
			 templateUrl: 'fragments/common/removeModal.html',
			 scope: $scope
		});
		
		modalInstance.result.then(function (result) {
			vm.userService = new userService();
	        vm.userService.$delete({userId: info.userId}, function() {
	        	vm.userDeleted = true;
	        	vm.dtInstance.reloadData(null, true);	// reload the datatable
	        });
		});
    }
	
    function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        // Unbind first in order to avoid any duplicate handler (see https://github.com/l-lin/angular-datatables/issues/87)
        $('td', nRow).unbind('click');
        // bind row click
        $('td', nRow).bind('click', function() {
            $scope.$apply(function() {
                vm.dtClickHandler(aData);
            });
        });
        
        // bind delete button click
        $('button', nRow).bind('click', function(event) {
            event.stopPropagation();
            $scope.$apply(function() {
                vm.dtDeleteHandler(aData);
            });
        });
        
        return nRow;
    }
    
}]);