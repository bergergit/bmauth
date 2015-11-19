package com.bergermobile.rest.controller;

import javassist.NotFoundException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bergermobile.rest.domain.ApplicationRest;
import com.bergermobile.rest.domain.FacebookRest;
import com.bergermobile.rest.domain.ForgotPasswordRestparameters;
import com.bergermobile.rest.domain.GoogleRest;
import com.bergermobile.rest.domain.ResetPasswordRestParameters;
import com.bergermobile.rest.domain.UserRest;
import com.bergermobile.rest.services.ApplicationService;
import com.bergermobile.rest.services.EmailService;
import com.bergermobile.rest.services.FormValidationException;
import com.bergermobile.rest.services.SerializableResourceBundleMessageSource;
import com.bergermobile.rest.services.UserService;

@RestController
@RequestMapping(value = "/bmauth")
public class UserCommandController {

	static Log LOG = LogFactory.getLog(UserCommandController.class);

	@Autowired
	private UserService userService;

	@Autowired
	SerializableResourceBundleMessageSource messageBundle;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ApplicationService applicationService;

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void saveUser(@Valid @RequestBody UserRest userRest, BindingResult result, HttpServletRequest request)
			throws FormValidationException {

		if (result.hasErrors()) {
			throw new FormValidationException(userRest.getUserId(), result);
		}

		// we are very smart, and will only allow saving of the roles if user is
		// bmauth-admin. Or else, a hacker may, you know... screw us up
		if (request.isUserInRole("ROLE_BMAUTH-ADMIN")) {
			userService.save(userRest, true);
		} else {
			userService.save(userRest, false);
		}

	}

	/**
	 * This will verify if we already have the social media user (Facebook,) in
	 * the DB. If we have, we just authenticate the user with USER role. Or else
	 * we create this user in the DB, and then authenticate
	 * 
	 * @param user
	 *            the userRest json object
	 */
	@RequestMapping(value = "/users/facebook", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveFacebookUser(@RequestBody FacebookRest facebookRest) {
		userService.saveFacebook(facebookRest);
	}

	/**
	 * This will verify if we already have the social media user (Google+) in
	 * the DB. If we have, we just authenticate the user with USER role. Or else
	 * we create this user in the DB, and then authenticate
	 * 
	 * @param user
	 *            the userRest json object
	 */
	@RequestMapping(value = "/users/google", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveGoogleUser(@RequestBody GoogleRest googleRest) {
		LOG.debug("UserCommandController. SaveGoogleUser for token " + googleRest.getAccessToken());
		userService.saveGoogle(googleRest);
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable int userId) {
		userService.delete(userId);
	}

	@RequestMapping(value = "/token/generate_token", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void generateToken(@Valid @RequestBody ForgotPasswordRestparameters forgotPasswordRestparameters,
			HttpServletRequest httpServletRequest) throws MessagingException, NotFoundException {

		// Check if this email has access to the application
		String appName = forgotPasswordRestparameters.getAppName();
		ApplicationRest applicationRest = applicationService.findByApplicationName(appName);
		UserRest userRest = userService.findByEmailAndApplicationId(forgotPasswordRestparameters.getEmail(),
				applicationRest.getApplicationId());

		if (userRest == null) {
			throw new NotFoundException("email not found for this application");
		}

		// Prepare to send email
		String urlApplication = applicationRest.getUrl();
		String token = userService.generateUserToken(userRest);
		String link = urlApplication + "/reset/" + token + "/" + userRest.getUserId();
		String subject = this.messageBundle.getMessage("email.subject", null, LocaleContextHolder.getLocale());
		String emailBody = userService.generateBodyMailForgotMyPassword(userRest, applicationRest, link);

		// send e-mail
		try {
			emailService.send(appName, userRest.getEmail(), subject, emailBody);
		} catch (Exception e) {
			LOG.error("UserCommandController. generateToken sending email " + e.getMessage());
		}

	}

	@RequestMapping(value = "/reset/reset_password", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void resetPassword(@RequestBody ResetPasswordRestParameters resetPasswordRestParameters,
			HttpServletRequest httpServletRequest) throws NotFoundException {

		boolean isValidToken = userService.validateUserToken(resetPasswordRestParameters.getUserId(),
				resetPasswordRestParameters.getToken());

		if (isValidToken == false) {
			throw new NotFoundException("Expired request");
		}

		UserRest userRest = userService.findByUserId(resetPasswordRestParameters.getUserId());
		userRest.setPassword(resetPasswordRestParameters.getPassword());
		userService.save(userRest, false);

	}

}
