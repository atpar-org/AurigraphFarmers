package com.example.aurigraph.farmers.Controller;
import com.example.aurigraph.farmers.Service.OtpService;
import com.example.aurigraph.farmers.Service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final TwilioService twilioService;
    private final OtpService otpService;

    @Autowired
    public SmsController(TwilioService twilioService, OtpService otpService) {
        this.twilioService = twilioService;
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public String sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        return twilioService.sendSms(phoneNumber, message);
    }

    @PostMapping("/send-otp")
    public String sendOtpSms(@RequestParam String phoneNumber) {
        return otpService.sendOtpSms(phoneNumber);
    }

    @PostMapping("/verify-otp")
    public boolean verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        return otpService.validateOtp(phoneNumber, otp);
    }
}
