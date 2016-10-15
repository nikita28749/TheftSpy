'use strict';

var mongoose = require('mongoose'),
    bCrypt = require('bcrypt'),
    User = mongoose.model('User');

var isValidPassword = function(password, userPwd) {
	return bCrypt.compareSync(password, userPwd);
};


exports.login = function(req, res) {
	User.findOne({'userName': req.body.userName} , function(err, doc) {
		if (err) {
			res.status(400).send({errorCode: 1, message: 'Failed to query user.'});
		} else {
			if (!doc) {
				res.status(200).send({errorCode: 1, message: 'User does not exist.'});
				console.log('Inside here');
			} else {
				if (isValidPassword(req.body.password, doc.password)) {
					res.status(200).send({errorCode: 0, message: 'Logged in successfully.'});
				} else {
					res.status(200).send({errorCode: 1, message: 'Failed to login into account.'});
				}
			}
		}
	});

};

// Check if account already exists, or else create a new account
// Only one admin account was created. The  new created account is set role
// to be user by default
exports.register =  function(req, res) {
	var user = new User(req.body);
	var message = null;
	User.findOne({'userName': user.userName}, function(err, doc) {
		if (err) {
			res.status(400).send({errorCode: 1, message: 'Failed to query user.'});
		} else {
			if (doc) {
				res.status(200).send({errorCode: 1, message: 'User already exists.'});
			} else {
				user.save(function(err) {
					if (err) {
						res.status(400).send({errorCode: 1, message: 'Failed to create an account.'});
					} else {
						res.status(200).send({errorCode: 0, message: 'Created account successfully.'});
					}
	
				});

			}
		}
	});
};

// Update user information
exports.update = function(req, res) {
	var userName = req.params.userName;
	var user = req.body;
	User.update({userName: userName}, user, {safe:true}, function(err, doc) {
		if (err) {
			res.status(400).send({errorCode: 1, message: 'Failed to update user info.'});
		} else {	
			res.status(200).send({errorCode: 0, message: 'Update user info successfully.'});
		}

	});

};
