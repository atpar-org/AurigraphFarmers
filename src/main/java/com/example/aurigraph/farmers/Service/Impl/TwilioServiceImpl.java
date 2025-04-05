package com.example.aurigraph.farmers.Service.Impl;

import com.example.aurigraph.farmers.Config.TwilioConfig;
import com.example.aurigraph.farmers.Service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {
    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioServiceImpl(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public String sendSms(String toPhoneNumber, String messageBody) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                    messageBody
            ).create();

            return "Message sent successfully, message SID: "+message.getSid(); // Return Twilio message ID
        } catch (Exception e) {
            return "Failed to send SMS: " + e.getMessage();
        }
    }
}
