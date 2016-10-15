'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	bCrypt = require('bcrypt'),
	crypto = require('crypto');

/**
 * User Schema
 */
var UserSchema = new Schema({
	userName: {
		type: String,
		trim: true
	},
	email: {
		type: String,
		trim: true,
		default: '',

	},
	password: {
		type: String,
		default: '',
	},
	phoneNumber: {
		type: String,
		default: ''
	},
});

/**
 * Hook a pre save method to hash the password
 */

UserSchema.pre('save', function(next) {
	if (this.password && this.password.length > 6) {
		this.password = bCrypt.hashSync(this.password, bCrypt.genSaltSync(10), null);
	}

	next();
});

mongoose.model('User', UserSchema);
