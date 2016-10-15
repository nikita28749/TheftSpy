'use strict';

/**
 * Module dependencies.
 */
var passport = require('passport');

module.exports = function(app) {
	// User Routes
	var users = require('../../app/controllers/users.server.controller');
	
	app.route('/users/login').post(users.login);
	app.route('/users/register').post(users.register);
	app.route('/users/update/:userName').put(users.update);
};
