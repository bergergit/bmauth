'use strict';

/* jasmine specs for controllers go here */
describe('Application controllers', function() {
	beforeEach(function() {
		jasmine.addMatchers({
			toEqualData : function() {
				return {
					compare : function(actual, expected) {
						return {
							pass : angular.equals(actual, expected)
						};
					}
				};
			}
		});
	});
	
	beforeEach(module('pascalprecht.translate'));
	beforeEach(module('bmauth.applications'));
	
	describe('ApplicationsListCtrl', function() {
		var scope = {}, ctrl, $httpBackend, $translate, createController;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, _$translate_) {
			$httpBackend = _$httpBackend_;
			$translate = _$translate_;
			$httpBackend.when('GET', 'undefinedbmauth/applications').respond( 
					[{
						applicationId : 1,
						applicationName: 'Bomber Cast'
					},{
						applicationId : 2,
						applicationName: 'Bomber Track'
					}]
			);

			scope = $rootScope.$new();
			
			createController = function() {
		       return $controller('ApplicationsListCtrl', {'$scope' : $rootScope });
		    };
			
			//ctrl = $controller('ApplicationsListCtrl', {$scope : scope});
		}));
		
		afterEach(function() {
		     $httpBackend.verifyNoOutstandingExpectation();
		     $httpBackend.verifyNoOutstandingRequest();
		});
		
		
		
		it('should invoke the applications rest service', inject(function() {
			$httpBackend.expectGET('undefinedbmauth/applications');
			var ctrl = createController();
			expect(ctrl.dtOptions).toBeDefined();
			
			$httpBackend.flush();
		}));
		
		it('should have a datatable HTML element with 2 rows', function() {
			
		});

	});
	
	describe('ApplicationsEditCtrl', function() {
		it('should have 2 datatables. One with 4 roles and the other with 2 contracts', function() {
			
		});
	});
});
