'use strict';

describe('Users service', function() {

	beforeEach(module('bmauth.users'));

	// Test service availability
	it('will check the existence of User factory', inject(function(userService) {
		expect(userService).toBeDefined();
	}));
});