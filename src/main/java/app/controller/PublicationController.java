package app.controller;

import app.dto.BorrowDto;
import app.dto.PublicationDto;
import app.dto.UserDto;
import app.exception.InvalidBorrowPublication;
import app.services.BorrowService;
import app.services.PublicationServices;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/publication")
public class PublicationController {

    private PublicationServices publicationServices;
    private UserService userService;
    private BorrowService borrowService;

    @Autowired
    public PublicationController(PublicationServices publicationServices, UserService userService, BorrowService borrowService) {
        this.publicationServices = publicationServices;
        this.userService = userService;
        this.borrowService = borrowService;
    }

    @GetMapping("/list")
    public String getListPublication(@RequestParam(required = false) String type, Model model) {
        List<PublicationDto> allPublication = Collections.emptyList();
        if(type == null) {
            allPublication = publicationServices.getAllPublication();
        } else if(type.equals("book")) {
            allPublication = publicationServices.getAllBook();
        } else if(type.equals("magazine")) {
            allPublication = publicationServices.getAllMagazine();
        }

        model.addAttribute("publications", allPublication);
        return "publication/list";
    }

    @GetMapping("/{id}/{type}/edit")
    public String editPublication(@PathVariable("id")Integer id,
                                  @PathVariable("type")String type) {

        if(type.equals("BOOK"))
            return "redirect:/book/" + id +"/edit";
        else
            return "redirect:/magazine/" + id +"/edit";
    }

    @GetMapping("/{id}/{type}/borrow")
    public String borrowBook(@PathVariable("id") Integer id,
                            @PathVariable("type")String type,
                            Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("id_publication",id);
        model.addAttribute("type", type);
        return "publication/borrow";
    }

    @GetMapping("/{id}/{id_publication}/{type}/saveBorrow")
    public String saveBorrow(@PathVariable("id") Integer id,
                             @PathVariable("id_publication") Integer id_publication,
                             @PathVariable("type")String type) {

        try {
            BorrowDto borrowDto = borrowService.saveBorrow(id, id_publication);
            System.out.println("Udało Ci sie wypoczyczyć: " + borrowDto);
        } catch (InvalidBorrowPublication e) {
            System.out.println(e.getMessage());
            return "redirect:/publication/list";
        }

        return "redirect:/publication/list";

    }

    @GetMapping("/{id}/returnBorrow")
    public String returnBorrow(@PathVariable("id") Integer id) {
        try {
            LocalDate endTimeBorrow = borrowService.returnBorrow(id);
            System.out.println("Udało Ci sie zwrocic ksiażke o godzinie: " + endTimeBorrow);
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/user/list";
    }



}
