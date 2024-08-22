package it.city.appedooserver.service;

import it.city.appedooserver.entity.*;
import it.city.appedooserver.entity.enums.RoleName;
import it.city.appedooserver.entity.enums.Weekday;
import it.city.appedooserver.payload.*;
import it.city.appedooserver.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    final
    GroupRepository groupRepository;

    final
    CategoryRepository categoryRepository;

    final
    UserRepository userRepository;
    final
    StudentRepository studentRepository;

    final AttendanceRepository attendanceRepository;

    public GroupService(GroupRepository groupRepository, CategoryRepository categoryRepository, UserRepository userRepository, StudentRepository studentRepository, AttendanceRepository attendanceRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public ApiResponse addGroup(ReqGroup reqGroup) {
//        if (user.getRoleSet().contains("ROLE_RECEPTION")  || user.getRoleSet().contains("ROLE_MANAGER") || user.getRoleSet().contains("ROLE_DIRECTOR")) {
        addOrEditGroup(reqGroup, new Group());
        return new ApiResponse("Add group", true);
//        }
//        return new ApiResponse("sizda bunday huquq yuq", false);
    }

    public ApiResponse editGroup(ReqGroup reqGroup, Integer id) {
//        if (user.getRoleSet().contains("ROLE_RECEPTION")  || user.getRoleSet().contains("ROLE_MANAGER") || user.getRoleSet().contains("ROLE_DIRECTOR")) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Get group"));
        addOrEditGroup(reqGroup, group);
        return new ApiResponse("Edit group", true);
//        }
//        return new ApiResponse("sizda bunday huquq yuq", false);
    }

    public void addOrEditGroup(ReqGroup reqGroup, Group group) {
        List<String> week = new ArrayList<>();
        group.setName(reqGroup.getName());
        group.setCategory(categoryRepository.findById(reqGroup.getCategoryId()).orElseThrow(() -> new ResourceAccessException("Get Category")));
        group.setStartHour(reqGroup.getStartHour());
        group.setFinishHour(reqGroup.getFinishHour());
        group.setLessonStartedDate(reqGroup.getLessonStartedDate());
        group.setLessonFinishDate(reqGroup.getLessonFinishDate());
        group.setTeacher(userRepository.findById(reqGroup.getTeacherId()).orElseThrow(() -> new ResourceAccessException("Get Teacher")));
        group.setWeekday(reqGroup.getWeekday());
        if (reqGroup.getWeekday().name().equals("ODD")) {
            week.add("Monday");
            week.add("Wednesday");
            week.add("Friday");
        } else if (reqGroup.getWeekday().name().equals("PAIR")) {
            week.add("Tuesday");
            week.add("Thursday");
            week.add("Saturday");
        } else if (reqGroup.getWeekday().name().equals("EVERYDAY")) {
            week.add("Monday");
            week.add("Tuesday");
            week.add("Wednesday");
            week.add("Thursday");
            week.add("Friday");
            week.add("Saturday");
        } else {
            week.addAll(reqGroup.getWeekdays());
        }
        group.setWeekdays(week);
        groupRepository.save(group);
    }

    public ApiResponse getTeacher() {
        List<User> users = userRepository.findAll();
        List<ResUser> resUsers = new ArrayList<>();
        for (User user : users) {
            for (Role role : user.getRoleSet()) {
                if (role.getRoleName().equals(RoleName.ROLE_TEACHER)) {
                    ResUser resUser = new ResUser();
                    resUser.setId(user.getId());
                    resUser.setName(user.getFirstName() + " " + user.getLastName());
                    resUser.setPhoneNumber(user.getPhoneNumber());
                    resUser.setUsername(user.getUsername());
                    resUser.setRole(String.valueOf(user.getRoleSet()));
                    resUsers.add(resUser);
                }
            }
        }
        return new ApiResponse(true, resUsers);
    }

    public ApiResponse getGroupList() {
        List<Group> groupList = groupRepository.findAll();
        List<ResGroup> resGroups = new ArrayList<>();
        for (Group group : groupList) {
            ResGroup resGroup = new ResGroup();
            resGroup.setId(group.getId());
            resGroup.setName(group.getName());
            resGroup.setCategory(group.getCategory());
            resGroup.setStartHour(group.getStartHour());
            resGroup.setFinishHour(group.getFinishHour());
            resGroup.setLessonStartedDate(group.getLessonStartedDate());
            resGroup.setLessonFinishDate(group.getLessonFinishDate());
            resGroup.setTeacher(group.getTeacher());
            resGroup.setWeekday(String.valueOf(group.getWeekday()));
            resGroups.add(resGroup);
        }
        return new ApiResponse(true, resGroups);
    }

    public ResGroup getGroup(Integer id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceAccessException("get Group"));
        return new ResGroup(
                group.getId(),
                group.getName(),
                group.getCategory(),
                group.getStartHour(),
                group.getFinishHour(),
                group.getLessonStartedDate(),
                group.getLessonFinishDate(),
                group.getTeacher(),
                group.getWeekday().name()
        );
    }


    public ApiResponse getGroupPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Group> groupPage = groupRepository.findAll(pageable);

        return new ApiResponse(true, new ResPageable(
                page,
                size,
                groupPage.getTotalPages(),
                groupPage.getTotalElements(),
                groupPage.getContent().stream().map(group -> getGroup(group.getId())).collect(Collectors.toList())

        ));
    }

    public ApiResponse deleteGroup(Integer id) {
        groupRepository.deleteById(id);
        return new ApiResponse("Group delete", true);
    }

    public ApiResponse setAttendance(Integer id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetGroup"));
        if (group.isActive()) {
            Attendance attendance = new Attendance();
            attendance.setGroup(group);
            for (Student student : studentRepository.findAllByGroup(group)) {
                attendance.setStudent(student);
                String dayOfWeek = String.valueOf(group.getWeekday());
                int month = Integer.parseInt(new SimpleDateFormat("MM").format(group.getLessonStartedDate())) - 1;
                int date = Integer.parseInt(new SimpleDateFormat("dd").format(group.getLessonStartedDate()));
                switch (dayOfWeek) {
                    case "ODD": {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, date);

                        int dayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

                        for (int i = 0; i <= dayOfMonth; i++) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) % 2 == 0) {
                                saveAttendance(attendance, calendar);
                            }
                            calendar.add(Calendar.DATE, 1);
                        }
                        break;
                    }
                    case "PAIR": {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, date);

                        int dayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

                        for (int i = 0; i <= dayOfMonth; i++) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) > 1 && calendar.get(Calendar.DAY_OF_WEEK) % 2 == 1) {
                                saveAttendance(attendance, calendar);
                            }
                            calendar.add(Calendar.DATE, 1);
                        }
                        break;
                    }
                    case "EVERYDAY": {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, date);

                        int dayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);

                        for (int i = 0; i <= dayOfMonth; i++) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) > 1) {
                                saveAttendance(attendance, calendar);
                            }
                            calendar.add(Calendar.DATE, 1);
                        }
                        break;
                    }
                    default: {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DATE, date);

                        int dayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
                        List<String> weekdays = group.getWeekdays();
                        for (int i = 0; i <= dayOfMonth; i++) {
                            if (calendar.get(Calendar.DAY_OF_WEEK) > 1 && weekdays.contains(new SimpleDateFormat("EEEE").format(calendar.getTime()))) {
                                saveAttendance(attendance, calendar);
                            }
                            calendar.add(Calendar.DATE, 1);
                        }
                    }
                }
            }

        }
        return new ApiResponse("Group need to be active", true);
    }

    private void saveAttendance(Attendance attendance, Calendar calendar) {
        attendance.setAttendanceDay(String.valueOf(calendar.getTime()));
        attendance.setWeekdayName(new SimpleDateFormat("EEEE").format(calendar.getTime()));
        attendanceRepository.save(attendance);
    }

    public ApiResponse getWeekday() {
        Weekday[] weekdays = Weekday.values();
        return new ApiResponse(true, weekdays);
    }


}
