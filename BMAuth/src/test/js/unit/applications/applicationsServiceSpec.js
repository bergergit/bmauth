'use strict';

describe('Applications service', function() {

	beforeEach(module('bmauth.applications'));

	// Test service availability
	it('will check the existence of Application factory', inject(function(applicationService) {
		expect(applicationService).toBeDefined();
	}));
});