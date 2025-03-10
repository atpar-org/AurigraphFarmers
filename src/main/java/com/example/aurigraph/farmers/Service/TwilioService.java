package com.example.aurigraph.farmers.Service;


import com.example.aurigraph.farmers.Config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class TwilioService {

    private final TwilioConfig twilioConfig;

    @Autowired
    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

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
