angular.module("bmauth.main")
/**
 * Deals with form errors and decorates the elements when they are there.
 */
.directive("bmauthFormErrors",['$translate','bmauthFormErrorsConfig', function($translate, bmauthFormErrorsConfig) {
	var directive = {};
	directive.restrict = 'A';
	directive.require = "^form";
	
	/**
	 * Returns the corresponding translated error message
	 */
	var translatedError = function(errors) {
		var text = "";
		for (error in errors) {
			text = $translate.instant("fieldErrors." + error);
			break;
		}
		return text;
	}
	
	
	directive.link = function(scope, el, attrs, form) {
		if (!bmauthFormErrorsConfig.enabled) return; 	// ignore if not enabled
		
		// find the form element inside this directive
		var inputElement = el.find('.form-control');
		if (inputElement) {
			var formName = inputElement.attr('name');
			if (!formName) {
				throw "Can't check for errors: Input form needs a 'name' property";
			}
			var inputNgElement = angular.element(inputElement);
			
			// bind lost focus event to perform validation
			inputNgElement.bind('blur', function() {
				if (form[formName].$touched) {
					el.toggleClass('has-error', form[formName].$invalid);

					// attaches a popover with information about the error
					if (form[formName].$invalid) {
						el.popover({ content: translatedError(form[formName].$error) }).popover('show');	
					} else {
						el.popover('destroy');	
					} 
					
				}
			})
			
			
		}
	}
	
	return directive;
}])

.provider('bmauthFormErrorsConfig', function() {
	var _enabled;
	_enabled = true;
	this.enabled = function(enabled) {
		return _enabled = enabled;
	};
	this.$get = function() {
		return {
			enabled : _enabled
		};
	};
});