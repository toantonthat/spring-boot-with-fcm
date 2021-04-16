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
import com.notification.model.SubscriptionRequestDto;
import com.notification.service.PushNotificationService;

@RestController
@RequestMapping("/notification")
public class PushNotificationController {

	@Autowired
	private PushNotificationService pushNotificationService;

	@PostMapping("/topic")
	public ResponseEntity<Object> sendNotification(@RequestBody PushNotificationRequest request) {
	  System.out.println("@@@");
		pushNotificationService.sendPushNotificationWithoutData(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}

	@PostMapping("/token")
	public ResponseEntity<Object> sendTokenNotification(@RequestBody PushNotificationRequest request) {
		pushNotificationService.sendPushNotificationToToken(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}

	@PostMapping("/data")
	public ResponseEntity<Object> sendDataNotification(@RequestBody PushNotificationRequest request) {
		pushNotificationService.sendPushNotification(request);
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}
	
	@PostMapping("/subscribe")
  public String subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
    return pushNotificationService.subscribeToTopic(subscriptionRequestDto);
  }
}
