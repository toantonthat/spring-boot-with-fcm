package com.notification.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
//@NoArgsConstructor
public class PushNotificationRequest {
	private String title;
	private String message;
	private String topic;
	private String token;
}
