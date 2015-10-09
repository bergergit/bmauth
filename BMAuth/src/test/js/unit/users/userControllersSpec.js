'use strict';

/* jasmine specs for controllers go here */
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
	
	
	beforeEach(module('ngRoute'));
	beforeEach(module('pascalprecht.translate'));
	beforeEach(module('bmauth.users'));
	beforeEach(module('bmauth.applications'));
	
	describe('UsersListCtrl', function() {
		var scope = {}, ctrl, $httpBackend, $translate, createController;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, _$translate_) {
			$httpBackend = _$httpBackend_;
			$translate = _$translate_;
			$httpBackend.when('GET', 'undefinedbmauth/users').respond( 
					[{
						userId : 1,
						name: 'Fabio Berger',
						email: 'fabioberger@gmail.com',
						type: 1
					},{
						userId : 1,
						name: 'Fabio Filz',
						email: 'fabiofilz@gmail.com',
						type: 2
					}]
			);
			
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
		       return $controller('UsersListCtrl', {'$scope' : $rootScope });
		    };
		}));
		
		afterEach(function() {
		     $httpBackend.verifyNoOutstandingExpectation();
		     $httpBackend.verifyNoOutstandingRequest();
		});
		
		
		
		it('should invoke the users rest service', inject(function() {
			$httpBackend.expectGET('undefinedbmauth/users');
			var ctrl = createController();
			expect(ctrl.dtOptions).toBeDefined();
			
			$httpBackend.flush();
		}));
	});
	
	describe('UsersEditCtrl', function() {
		var scope = {}, ctrl, $httpBackend, $translate, createController;

		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, _$translate_, $routeParams) {
			$httpBackend = _$httpBackend_;
			$translate = _$translate_;
			$httpBackend.when('GET', 'undefinedbmauth/users/1').respond({
				userId : 1,
				name: 'Fabio Berger',
				email: 'fabioberger@gmail.com',
				type: 1
			});
			
			$routeParams.userId = 1;
			scope = $rootScope.$new();
			
			createController = function() {
		       return $controller('UsersEditCtrl', {'$scope' : scope });
		    };
		}));
		
		afterEach(function() {
		     $httpBackend.verifyNoOutstandingExpectation();
		     $httpBackend.verifyNoOutstandingRequest();
		});
		
	    it('should make a request to /users/1', function() {
	    	$httpBackend.expectGET('undefinedbmauth/users/1');
	    	var ctrl = createController();
	    	$httpBackend.flush();
	    	
	    	expect(ctrl.dtOptions).toBeDefined();
	    	expect(ctrl.signup.name).toBe('Fabio Berger')
	    	
	    });
	});
});
