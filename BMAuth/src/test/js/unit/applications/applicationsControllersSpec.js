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
		var scope, ctrl, $httpBackend, $translate, applicationService;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, _$translate_, _applicationService_) {
			$httpBackend = _$httpBackend_;
			$translate = _$translate_;
			$httpBackend.expectGET('bmauth/applications').respond(200, 
					[{
						applicationId : 1,
						applicationName: 'Bomber Cast'
					},{
						applicationId : 2,
						applicationName: 'Bomber Track'
					}]
			);

			$rootScope.authContext='';
			scope = $rootScope.$new();
			$rootScope.authContext='';

			ctrl = $controller('ApplicationsListCtrl', {$scope : scope});
			
			applicationService = _applicationService_;
			spyOn(applicationService, 'query');
		}));
		
		
		
		it('should invoke the applications rest service', inject(function(applicationService) {
			//debugger;
			
			
			
			expect(applicationService).toBeDefined();
			expect(ctrl.dtOptions).toBeDefined();
			
			$httpBackend.flush();
			
			expect(applicationService.query).toHaveBeenCalled();
			
			//expect(ctrl.applicationService.length).toBe(2);
		}));
		
		it('should have a datatable HTML element with 2 rows', function() {
			
		});

	});
	
	describe('ApplicationsEditCtrl', function() {
		it('should have 2 datatables. One with 4 roles and the other with 2 contracts', function() {
			
		});
	});
});
