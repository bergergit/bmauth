angular.module('bmauth.applications', ['datatables', 'datatables.bootstrap', 'ngResource'])

/**
 * Application Edit controller
 */
.controller('ApplicationsEditCtrl', ['$scope', function($scope) {
	
	console.debug("in applications controller");
	
	var vm = this;
	
	$scope.application = {"active": true, "testMode": "0"};
}])

/**
 * Application List controller
 */
.controller('ApplicationsListCtrl', ['$scope','DTOptionsBuilder','DTColumnBuilder','Application', '$translate', '$location',
                                     function($scope, DTOptionsBuilder, DTColumnBuilder, Application, $translate, $location) {
	
	console.debug("in applications list controller");
	
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
	
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var promise = Application.query().$promise;
		promise.then(function() {
			vm.ready = true;
		})
        return promise;
    })
    .withOption('rowCallback', rowCallback)
    .withBootstrap();
	
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