package it.city.appedooserver.service;

import it.city.appedooserver.entity.Group;
import it.city.appedooserver.entity.PayType;
import it.city.appedooserver.entity.Payment;
import it.city.appedooserver.entity.Student;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.payload.ReqPayment;
import it.city.appedooserver.payload.ResPayment;
import it.city.appedooserver.repository.GroupRepository;
import it.city.appedooserver.repository.PayTypeRepository;
import it.city.appedooserver.repository.PaymentRepository;
import it.city.appedooserver.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PayTypeRepository payTypeRepository;
    @Autowired
    PaymentRepository paymentRepository;

    public ApiResponse addPayment(ReqPayment reqPayment) {
        Group group = groupRepository.findById(reqPayment.getGroupId()).orElseThrow(() -> new ResourceAccessException("addPAyment"));
        Student student = studentRepository.findById(reqPayment.getStudentId()).orElseThrow(() -> new ResourceAccessException("getStudent"));
        PayType payType = payTypeRepository.findById(reqPayment.getPayTypeId()).orElseThrow(() -> new ResourceAccessException("payType"));
        Payment payment = new Payment();
        payment.setPaySum(reqPayment.getPaySum());
        payment.setPayDate(reqPayment.getPayDate());
        payment.setPayType(payType);
        payment.setGroup(group);
        payment.setStudent(student);
        paymentRepository.save(payment);
        return new ApiResponse("Successfully added payment", true);
    }

    public ApiResponse deletePayment(UUID id) {
        paymentRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }

    public ApiResponse updatePayment(UUID id, ReqPayment reqPayment){
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            Optional<Group> group = groupRepository.findById(reqPayment.getGroupId());
            if (group.isPresent()) {
                Optional<Student> student = studentRepository.findById(reqPayment.getStudentId());
                if (student.isPresent()) {
                    Optional<PayType> payType = payTypeRepository.findById(reqPayment.getPayTypeId());
                    if (payType.isPresent()) {
                        Payment payment1 = payment.get();
                        payment1.setStudent(student.get());
                        payment1.setPaySum(reqPayment.getPaySum());
                        payment1.setGroup(group.get());
                        payment1.setPayType(payType.get());
                        payment1.setPayDate(reqPayment.getPayDate());
                        paymentRepository.save(payment1);
                        return new ApiResponse("Successfully updated", true);
                    }
                }
            }
        }
        return new ApiResponse("Error", false);
    }
    public ApiResponse get() {
        List<Payment> paymentList = paymentRepository.findAll();
        List<ResPayment> resPayments = new ArrayList<>();
        for (Payment payment : paymentList) {
            ResPayment resPayment = new ResPayment();
            resPayment.setPayType(payment.getPayType());
            resPayment.setStudent(payment.getStudent());
            resPayment.setGroup(payment.getGroup());
            resPayment.setPaySum(payment.getPaySum());
            resPayment.setPayDate(payment.getPayDate());
            resPayments.add(resPayment);
        }
        return new ApiResponse(true, resPayments);
    }
}


