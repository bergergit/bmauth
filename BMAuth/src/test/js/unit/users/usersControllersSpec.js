'use strict';

/* jasmine specs for controllers go here */
/*
describe('User controllers', function() {
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
		var scope, ctrl, $httpBackend, $translate;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, _$translate_) {
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

			scope = $rootScope.$new();
			ctrl = $controller('ApplicationsListCtrl', {$scope : scope});
		}));
		
		it('should invoke the applications rest service', inject(function(Application) {
			expect(Application).toBeDefined();
			//expect(scope.Application.length).toBe(2);
		}));
		
		it('should have a datatable HTML element with 2 rows', function() {
			
		});

	});
});
*/
