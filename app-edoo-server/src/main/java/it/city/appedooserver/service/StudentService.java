package it.city.appedooserver.service;

import it.city.appedooserver.entity.Aware;
import it.city.appedooserver.entity.Group;
import it.city.appedooserver.entity.Student;
import it.city.appedooserver.entity.enums.CameFromEnum;
import it.city.appedooserver.entity.enums.UserStatus;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.payload.ReqStudent;
import it.city.appedooserver.payload.ResPageable;
import it.city.appedooserver.payload.ResStudent;
import it.city.appedooserver.repository.AwareRepository;
import it.city.appedooserver.repository.GroupRepository;
import it.city.appedooserver.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    final
    StudentRepository studentRepository;
    final GroupRepository groupRepository;
    final AwareRepository awareRepository;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository, AwareRepository awareRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;

        this.awareRepository = awareRepository;
    }

    public ApiResponse addStudent(ReqStudent reqStudent) {
        saveStudent(reqStudent, new Student());
        return new ApiResponse("UserSaved", true);
    }


    public ApiResponse getStudents() {
        List<Student> students = studentRepository.findAll();
        List<ResStudent> resStudents = new ArrayList<>();
        for (Student student : students) {
            ResStudent resStudent = new ResStudent();
            resStudent.setFirstName(student.getFirstName());
            resStudent.setLastName(student.getLastName());
            resStudent.setPhoneNumber(student.getPhoneNumber());
            resStudent.setAge(student.getAge());
            resStudent.setRegistrationDay(student.getRegistrationDay());
            resStudent.setId(student.getId());
            resStudent.setDiscount(student.getDiscount());
            resStudent.setUserStatus(String.valueOf(student.getUserStatus()));
            resStudent.setCameFrom(String.valueOf(student.getCameFromEnum()));
            resStudent.setGroupId(student.getGroup().getId());
            resStudent.setGroupName(student.getGroup().getName());
            resStudent.setAwareId(student.getAware().getId());
            resStudent.setAwareName(student.getAware().getName());
            resStudents.add(resStudent);
        }
        return new ApiResponse(true, resStudents);
    }

    public ApiResponse deleteStudent(UUID id) {
        studentRepository.deleteById(id);
        return new ApiResponse("Student successfully deleted", true);
    }

    public ApiResponse updateStudent(UUID id, ReqStudent reqStudent) {
        saveStudent(reqStudent, studentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("User Not Found")));
        return new ApiResponse("User successfully updated", true);
    }

    private void saveStudent(ReqStudent reqStudent, Student student) {
        Optional<Group> byId = groupRepository.findById(reqStudent.getGroupId());
        Aware aware = awareRepository.findById(reqStudent.getAwareId()).orElseThrow(() -> new ResourceAccessException("Aware Not found"));
        if (byId.isPresent()) {
            student.setGroup(byId.get());
            student.setUserStatus(UserStatus.valueOf(reqStudent.getUserStatus()));
        }else {
            student.setGroup(null);
            student.setUserStatus(UserStatus.NEW);
        }
        student.setFirstName(reqStudent.getFirstName());
        student.setLastName(reqStudent.getLastName());
        student.setPhoneNumber(reqStudent.getPhoneNumber());
        student.setAge(reqStudent.getAge());
        student.setRegistrationDay(reqStudent.getRegistrationDay());
        student.setDiscount(reqStudent.getDiscount());
        student.setCameFromEnum(CameFromEnum.valueOf(reqStudent.getCameFrom()));
        student.setAware(aware);
        studentRepository.save(student);
    }

    public ResStudent getStudent(UUID id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetStudent"));
        ResStudent resStudent = new ResStudent();
        resStudent.setFirstName(student.getFirstName());
        resStudent.setLastName(student.getLastName());
        resStudent.setPhoneNumber(student.getPhoneNumber());
        resStudent.setAge(student.getAge());
        resStudent.setRegistrationDay(student.getRegistrationDay());
        resStudent.setId(student.getId());
        resStudent.setDiscount(student.getDiscount());
        resStudent.setUserStatus(String.valueOf(student.getUserStatus()));
        resStudent.setCameFrom(String.valueOf(student.getCameFromEnum()));
        resStudent.setGroupId(student.getGroup().getId());
        resStudent.setGroupName(student.getGroup().getName());
        resStudent.setAwareId(student.getAware().getId());
        resStudent.setAwareName(student.getAware().getName());
        return resStudent;
    }

    public ApiResponse getStudentPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return new ApiResponse(true, new ResPageable(
                page,
                size,
                studentPage.getTotalPages(),
                studentPage.getTotalElements(),
                studentPage.getContent().stream().map(student -> getStudent(student.getId())).collect(Collectors.toList())
        ));
    }

    public ApiResponse getUserStatus(){
        UserStatus[] values = UserStatus.values();
        return new ApiResponse(true,values);
    }

    public ApiResponse getCameFrom(){
        CameFromEnum[] values = CameFromEnum.values();
        return new ApiResponse(true,values);
    }
}