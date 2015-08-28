'use strict';

describe('Home services', function() {

	beforeEach(module('bmauth.home'));

	// Test service availability
	it('check the existence of Signup factory', inject(function(Signup) {
		expect(Signup).toBeDefined();
	}));
});