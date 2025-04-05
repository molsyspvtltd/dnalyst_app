package com.example.app_server;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Autowired
    private TwilioConfig twilioConfig;

    public void sendSMS(String to, String message) {
        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(twilioConfig.getFromPhoneNumber()),
                        message)
                .create();
    }
}

