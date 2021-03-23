package com.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.model.PushNotificationRequest;
import com.notification.model.PushNotificationResponse;
import com.notification.service.PushNotificationService;

@RestController
@RequestMapping("/notification")
public class PushNotificationController {

	@Autowired
	private PushNotificationService pushNotificationService;

	@PostMapping("/notification/topic")
	public ResponseEntity<Object> sendNotification(@RequestBody PushNotificationRequest request) {
		pushNotificationService.sendPushNotificationWithoutData(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}

	@PostMapping("/notification/token")
	public ResponseEntity<Object> sendTokenNotification(@RequestBody PushNotificationRequest request) {
		pushNotificationService.sendPushNotificationToToken(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}

	@PostMapping("/notification/data")
	public ResponseEntity<Object> sendDataNotification(@RequestBody PushNotificationRequest request) {
		pushNotificationService.sendPushNotification(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}
}
