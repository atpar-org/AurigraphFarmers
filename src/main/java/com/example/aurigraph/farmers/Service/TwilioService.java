package com.example.aurigraph.farmers.Service;


import com.example.aurigraph.farmers.Config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public interface TwilioService {


    String sendSms(String toPhoneNumber, String messageBody);
}
