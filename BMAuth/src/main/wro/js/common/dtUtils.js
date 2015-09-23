angular.module('bmauth.main', ['datatables'])
.factory('DTLoadingTemplate', function() {
    return {
        html: '<h1>...</h1>'
    };
});


angular.module('datatables')
.factory('dtUtils', [function() {
	var dtUtils = {
		instance: null,
		$scope: null, 
		
		init: function(instance, scope) {
			dtUtils.instance = instance;
			dtUtils.$scope = scope; 
		},
	
		rowCallback: function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
	        // Unbind first in order to avoid any duplicate handler (see https://github.com/l-lin/angular-datatables/issues/87)
	        $('td', nRow).unbind('click');
	        // bind row click
	        $('td', nRow).bind('click', function() {
	            dtUtils.$scope.$apply(function() {
	            	dtUtils.instance.dtClickHandler(aData);
	            });
	        });
	        
	        // bind delete button click
	        $('button', nRow).bind('click', function(event) {
	            event.stopPropagation();
	            dtUtils.$scope.$apply(function() {
	            	dtUtils.instance.dtDeleteHandler(aData);
	            });
	        });
	        
	        return nRow;
	    }
	}
	
	return dtUtils;
}]);