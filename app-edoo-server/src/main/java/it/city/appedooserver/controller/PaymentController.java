package it.city.appedooserver.controller;

import it.city.appedooserver.payload.ReqPayment;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.payload.ResPayment;
import it.city.appedooserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/add")
    public HttpEntity<?> addPayment(@RequestBody ReqPayment reqPayment){
        ApiResponse apiResponse = paymentService.addPayment(reqPayment);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/del/{id}")
    public HttpEntity<?> deletePayment(@PathVariable UUID id) {
        ApiResponse apiResponse = paymentService.deletePayment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> updatePayment(@PathVariable UUID id, @RequestBody ReqPayment reqPayment) {
        ApiResponse apiResponse = paymentService.updatePayment(id,reqPayment);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/get")
    public HttpEntity<?> getPayment() {
        ApiResponse apiResponse = paymentService.get();
         return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
