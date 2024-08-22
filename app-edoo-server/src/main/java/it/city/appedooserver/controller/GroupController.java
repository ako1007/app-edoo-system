package it.city.appedooserver.controller;

import it.city.appedooserver.entity.enums.Weekday;
import it.city.appedooserver.payload.ReqGroup;
import it.city.appedooserver.payload.ApiResponse;
import it.city.appedooserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @PostMapping()
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> addGroup(@RequestBody ReqGroup group) {
        ApiResponse apiResponse = groupService.addGroup(group);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/setAttendance/{id}")
    public HttpEntity<?> set(@PathVariable Integer id) {
        ApiResponse apiResponse = groupService.setAttendance(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }
    @PutMapping("/edit/{id}")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> editGroup(@PathVariable Integer id, @RequestBody ReqGroup reqGroup) {
        ApiResponse apiResponse = groupService.editGroup(reqGroup, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
    @GetMapping("/getTeacher")
    public HttpEntity<?> getTeacher(){
        return ResponseEntity.ok(groupService.getTeacher());
    }


    @GetMapping("/list")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> getGroupList() {
        return ResponseEntity.ok( groupService.getGroupList());
    }

    @DeleteMapping("/delete/{id}")
//    @Secured({ "ROLE_RECEPTION", "ROLE_MANAGER", "ROLE_DIRECTOR" })
    public HttpEntity<?> deleteGroup(@PathVariable Integer id) {
        ApiResponse apiResponse = groupService.deleteGroup(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/page")
    public HttpEntity<?> getGroupPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size){
        return  ResponseEntity.ok(groupService.getGroupPage(page, size));
    }
    @GetMapping("/weekday")
    public HttpEntity<?> getWeekday(){
        return ResponseEntity.ok().body(groupService.getWeekday());
    }
}
