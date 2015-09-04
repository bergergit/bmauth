angular.module("bmauth.main", []).directive("bmAuth", function() {
	var directive = {};
	//directive.restrict = 'AE';

	directive.scope = {
		appName : '@'
	};

	directive.templateUrl = "/bmauth/fragments/home/login.html";

	directive.link = function(scope, elements, attr) {
	
		console.debug("appName", scope.appName);
	}
	
	return directive;
});