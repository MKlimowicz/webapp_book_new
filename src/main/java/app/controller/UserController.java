package app.controller;


import app.dto.UserBorrowDto;
import app.dto.UserDto;
import app.exception.UserAlreadyExistsException;
import app.exception.UserNoAlreadyExistsException;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/list")
    public String users(Model model){
        List<UserDto> users = userService.getUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user",new UserDto());
        return "user/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") UserDto userDto) {
        try {
            UserDto dto = userService.save(userDto);
            System.out.println("Udało Ci sie zapisać użytkownika : "  + dto);
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/user/add";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")Integer id, Model model) {
        try {
            UserDto dto =userService.findById(id);
            model.addAttribute("user",dto);
            return "user/edit";
        } catch (UserNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") UserDto userDto) {
        try {
            UserDto updatedDto = userService.update(userDto);
            System.out.println("Udalo Ci sie wyedytować: " + updatedDto);

        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/user/" + userDto.getId() +"/edit";
        }
        return "redirect:/user/list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id")Integer id) {
        try {
            UserDto userDto = userService.delete(id);
            System.out.println("Entity deleted: " + userDto);
            return "redirect:/user/list";
        } catch (UserNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/user/" + id + "/edit";
        }
    }

    @GetMapping("/{id}/borrowPublications")
    public String borrowPublications(@PathVariable("id")Integer id, Model model) {
        try {
            List<UserBorrowDto> borrowPublication = userService.findBorrowPublication(id);
            model.addAttribute("borrows", borrowPublication);
        } catch (UserNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "user/borrow";
    }
}
