package it.city.appedooserver.service;

import it.city.appedooserver.entity.Role;
import it.city.appedooserver.entity.Student;
import it.city.appedooserver.entity.User;
import it.city.appedooserver.entity.enums.RoleName;
import it.city.appedooserver.payload.*;
import it.city.appedooserver.repository.RoleRepository;
import it.city.appedooserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new ResourceAccessException("getUser"));
    }

    public ApiResponse register(ReqUser reqUser){
        if (!reqUser.getPassword().equals(reqUser.getPrePassword()))
            return new ApiResponse("Password and PrePassword must be equal;",false);
        if (userRepository.existsByUsernameEqualsIgnoreCaseAndPhoneNumber(reqUser.getUsername(), reqUser.getPhoneNumber()))
            return new ApiResponse("Username or Phone number already exists",false);
        if (reqUser.getPassword().equals(reqUser.getPrePassword())&& reqUser.getPassword().length()>=8) {
            saveOrEditUser(reqUser,new User());
            return new ApiResponse("You successfully registered",true);
        }
        return new ApiResponse("Password and prePassword must be equal",false);
    }

    public ApiResponse login(ReqLogin reqLogin){
        User user = userRepository.findByUsernameEqualsIgnoreCase(reqLogin.getUsername()).orElseThrow(() -> new ResourceAccessException("getByUsername"));
        if (passwordEncoder.matches(reqLogin.getPassword(), user.getPassword())) {
            return new ApiResponse("Logged in",true);
        }
        return new ApiResponse("Wrong password",false);
    }

    public ApiResponse deleteUser(UUID id){
        userRepository.deleteById(id);
        return new ApiResponse("User successfully deleted",true);
    }


    public ApiResponse updateUser(UUID id, ReqUser reqUser){
        boolean b = userRepository.existsByUsernameEqualsIgnoreCaseAndPhoneNumberAndIdNot(reqUser.getUsername(), reqUser.getPhoneNumber(), id);
        if (!b){
            if (reqUser.getPassword().equals(reqUser.getPrePassword())&& reqUser.getPassword().length()>=8) {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceAccessException("GetUSer"));
            saveOrEditUser(reqUser,user);
                return new ApiResponse("Your profile successfully updated",true);
            }
            return new ApiResponse("Password and prePassword must be equal",false);
        }
        return new ApiResponse("Username or Phone number already exists",false);
    }

    public ApiResponse setRole(ReqUserRole reqUserRole){//director uchun
        User user = userRepository.findById(reqUserRole.getId()).orElseThrow(() -> new ResourceAccessException("USER_NOT_FOUND"));
        Set<Role> role= Collections.singleton(roleRepository.findByRoleName(RoleName.valueOf(reqUserRole.getRoleName())));
        user.setRoleSet(role);
        user.setEnabled(true);
        return new ApiResponse("User role successfully set",true);
    }

    public ApiResponse getUserList(){
        List<User> users = userRepository.findAll();
        List<ResUser>resUsers=new ArrayList<>();
        ResUser resUser=new ResUser();
        for (User user : users) {
            resUser.setId(user.getId());
            resUser.setName(user.getFirstName()+" "+user.getLastName());
            resUser.setUsername(user.getUsername());
            resUser.setPhoneNumber(user.getPhoneNumber());
            resUser.setRole(user.getRoleSet().toString());
            resUsers.add(resUser);
        }
        return new ApiResponse(true, resUsers);
    }

    public void saveOrEditUser(ReqUser reqUser,User user){
            String encode = passwordEncoder.encode(reqUser.getPassword());
            user.setFirstName(reqUser.getFirstName());
            user.setLastName(reqUser.getLastName());
            user.setPhoneNumber(reqUser.getPhoneNumber());
            user.setUsername(reqUser.getUsername());
            user.setRoleSet(Collections.singleton(roleRepository.findByRoleName(RoleName.NONE)));
            user.setPassword(encode);
            userRepository.save(user);
    }

    public ResUser getOneUser(UUID id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceAccessException("getUser"));
        ResUser resUser=new ResUser();
        resUser.setId(user.getId());
        resUser.setName(user.getFirstName()+" "+user.getLastName());
        resUser.setUsername(user.getUsername());
        resUser.setPhoneNumber(user.getPhoneNumber());
        resUser.setRole(user.getRoleSet().toString());
        return resUser;
    }

    public ApiResponse getUserPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return new ApiResponse(true, new ResPageable(
                page,
                size,
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                userPage.getContent().stream().map(user -> getOneUser(user.getId())).collect(Collectors.toList())
        ));
    }





    public UserDetails getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
    }
}
