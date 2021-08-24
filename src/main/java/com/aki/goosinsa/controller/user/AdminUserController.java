package com.aki.goosinsa.controller.user;

import com.aki.goosinsa.domain.entity.user.UserDto;
import com.aki.goosinsa.domain.entity.user.UserRole;
import com.aki.goosinsa.repository.user.QDUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Log4j2
public class AdminUserController {

    private final QDUserRepository qdUserRepository;

    @ModelAttribute("userRole")
    public static UserRole[] userRoles(){
        return UserRole.values();
    }

    @GetMapping("/userList")
    public @ResponseBody List<UserDto> users(Model model, @RequestParam(defaultValue = "0") int pageNum, UserSearch userSearch){
        PageRequest pageable = PageRequest.of(pageNum, 10);
        Page<UserDto> userDtos = qdUserRepository.usersPaging(pageable, userSearch);
        return userDtos.getContent();
    }

    @GetMapping("/users")
    public String userList(Model model, @RequestParam(defaultValue = "0") int pageNum, UserSearch userSearch){
        log.info("============================>>   "  + userSearch.toString());
        PageRequest pageable = PageRequest.of(pageNum, 10);
        Page<UserDto> pages = qdUserRepository.usersPaging(pageable, userSearch);
        model.addAttribute("userDtoList", pages.getContent());
        model.addAttribute("pages", pages);
        model.addAttribute("maxPage", 10);
        return "/admin/user/users";
    }

}
