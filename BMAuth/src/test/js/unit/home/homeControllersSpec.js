'use strict';

/* jasmine specs for controllers go here */
describe('Home controllers', function() {
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
	
	
	beforeEach(module('bmauth.home'));
	
	describe('LoginCtrl', function() {
		var scope, ctrl, $httpBackend;
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller) {
			$httpBackend = _$httpBackend_;
			$httpBackend.expectPOST('login/internal').respond(201, {
				username : 'fabioberger'
			});

			scope = $rootScope.$new();
			ctrl = $controller('LoginCtrl', {$scope : scope});
		}));

		/*
		it('should retrieve a user object after successful login', function() {
			expect(scope.phones).toEqualData([]);
			$httpBackend.flush();

			expect(scope.phones).toEqualData([ {
				name : 'Nexus S'
			}, {
				name : 'Motorola DROID'
			} ]);
		});

		it('should set the default value of orderProp model', function() {
			expect(scope.orderProp).toBe('age');
		});
		*/
	});

	describe('SignupCtrl', function() {
		var scope, ctrl, $location, $httpBackend, userData = function() {
			return {
				username: 'fabioberger@gmail.com',
				user_id: 1
			}
		};

		beforeEach(inject(function(_$httpBackend_, $rootScope, _$location_, $controller) {
			$httpBackend = _$httpBackend_;
			$httpBackend.whenPOST('bmauth/users').respond(201);
			$location = _$location_;
			spyOn($location, 'path').and.returnValue('/applications');

			//$routeParams.phoneId = '5';
			
			scope = $rootScope.$new();
			ctrl = $controller('SignupCtrl', {$scope : scope});
			
		}));


		it('should create a new user when clicking on save button', inject(function(Signup) {
			expect(Signup).toBeDefined();
			
			scope.signup = new Signup(userData());
			
			//scope.signup = userData();
			//expect(scope.signup).toEqualData(userData());
			scope.submitSignup();
			$httpBackend.expectPOST('bmauth/users');
			$httpBackend.flush();

			//expect(scope.signup.query()).toEqualData(userData());
		}));
		
		it ('should redirect to /applications admin page', function() {
			scope.submitSignup();
			$httpBackend.flush();
			expect($location.path).toHaveBeenCalled();
			expect($location.path()).toBe('/applications')
		});
		
	});
	
});
