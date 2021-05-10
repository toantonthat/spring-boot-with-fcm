package com.notification.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.notification.model.PushNotificationRequest;

@Service
public class PushNotificationService {
	private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
	
	@Autowired
	private FCMService fcmService;
	
//	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	@Scheduled(fixedRate = 5000)
    public void sendSamplePushNotification() {
		logger.info("----> send a message");
        try {
            fcmService.sendMessageWithoutData(PushNotificationRequest.builder()
            		.title("Test 1")
            		.message("Message Sample")
            		.topic("Topic Test")
            		.build());
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
	
	public void sendPushNotification(PushNotificationRequest request) {
		try {
			fcmService.sendMessage(getSamplePayloadData(), request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotificationWithoutData(PushNotificationRequest request) {
		try {
			fcmService.sendMessageWithoutData(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void sendPushNotificationToToken(PushNotificationRequest request) {
		try {
			fcmService.sendMessageToToken(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private Map<String, String> getSamplePayloadData() {
		Map<String, String> pushData = new HashMap<>();
		pushData.put("messageId", "msgid");
		pushData.put("text", "txt");
		pushData.put("user", "pankaj singh");
		return pushData;
	}
}
