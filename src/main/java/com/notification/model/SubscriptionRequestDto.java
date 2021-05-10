package com.notification.model;

import java.io.Serializable;
import java.util.List;

public class SubscriptionRequestDto implements Serializable {

    private static final long serialVersionUID = -7748554581173981614L;
    String topicName;
    List<String> tokens;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

}
