'use strict';

describe('Applications services', function() {

	beforeEach(module('bmauth.applications'));

	// Test service availability
	it('check the existence of Application factory', inject(function(Application) {
		expect(Application).toBeDefined();
	}));
});