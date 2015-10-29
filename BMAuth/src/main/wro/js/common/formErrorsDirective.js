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
		
		var formName;
		
		// toggle error class and add a popover
		var toggleClasses = function(invalid, error) {
			el.toggleClass('has-error', invalid);

			// attaches a popover with information about the error
			if (invalid) {
				el.popover({ content: translatedError(error) }).popover('show');	
			} else {
				el.popover('destroy');	
			}
		}
		
		// find the form element inside this directive
		var inputElement = el.find('.form-control');
		if (inputElement) {
			formName = inputElement.attr('name');
			if (!formName) {
				throw "Can't check for errors: Input form needs a 'name' property";
			}
			var inputNgElement = angular.element(inputElement);
			
			// bind lost focus event to perform validation
			inputNgElement.bind('blur', function() {
				if (form[formName].$touched) {
					//el.toggleClass('has-error', form[formName].$invalid);
					toggleClasses(form[formName].$invalid, form[formName].$error);
				}
			});
			
			// broadcast event to decorate form with errors;
			scope.$on('check-validity', function() {
				toggleClasses(form[formName].$invalid, form[formName].$error);
	        });
			
			// adding a submit listener to the form so we can check for errors on submit as well
			el.parents('form').on('submit', function() {
				scope.$broadcast('check-validity');
			});
			
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
})

/**
 * Custom validator directive, to check if passwords match 
 */
.directive('bmauthPasswordMatch', function() {
	return {
		require: 'ngModel',
		restrict: 'A',
		scope: {
			matchWithId: '@'
		},
		link : function(scope, el, attrs, ctrl) {
			ctrl.$validators.bmauthPasswordMatch = function(modelValue, viewValue) {
				if (ctrl.$isEmpty(modelValue)) {
					// consider empty models to be valid
					return true;
				}

				// gets the id to match with
				var matchElement = el.parents('form').find('#' + scope.matchWithId);
				if (matchElement && matchElement.length > 0 && matchElement.val() === modelValue) {
					// it is valid
					return true;
				}
				
				// it is invalid
				return false;
			};
		}
	};
});

