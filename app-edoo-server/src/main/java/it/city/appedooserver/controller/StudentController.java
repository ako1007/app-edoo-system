package it.city.appedooserver.controller;

import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.payload.ReqStudent;
import it.city.appedooserver.service.StudentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/student")
public class StudentController {
    final
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/add")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> addStudent(@RequestBody ReqStudent reqStudent){
        ApiResponse apiResponse = studentService.addStudent(reqStudent);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/get")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> getStudent(){
        ApiResponse student = studentService.getStudents();
        return ResponseEntity.ok().body(student);
    }
    @PutMapping("/update/{id}")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> updateStudent(@PathVariable UUID id,@RequestBody ReqStudent reqStudent){
        ApiResponse apiResponse = studentService.updateStudent(id, reqStudent);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/{id}")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> deleteStudent(@PathVariable UUID id){
        ApiResponse apiResponse = studentService.deleteStudent(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/page")
    public HttpEntity<?> getStudentPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size){
        return  ResponseEntity.ok(studentService.getStudentPage(page, size));
    }

    @GetMapping("/status")
    public HttpEntity<?> getStatus(){
        ApiResponse status = studentService.getUserStatus();
        return ResponseEntity.ok().body(status);
    }
    @GetMapping("/came")
    public HttpEntity<?> getCameFrom(){
        ApiResponse came = studentService.getCameFrom();
        return ResponseEntity.ok().body(came);
    }



}
