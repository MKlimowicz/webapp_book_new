package app.controller;

import app.dto.BookBorrowDto;
import app.dto.BookDto;
import app.exception.BookAlreadyExistsException;
import app.exception.BookNoAlreadyExistsException;
import app.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController  {

    private BookServices bookServices;

    @Autowired
    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }


    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDto());
        return "book/add";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book")BookDto bookDto) {
        try {
            BookDto savedBook = bookServices.save(bookDto);
            System.out.println("Udało się zapisać -> " + savedBook);
        }catch (BookAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/publication/list?type=book";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id")Integer id, Model model) {
        try {
            BookDto dto = bookServices.findBookById(id);
            model.addAttribute("book", dto);
            return "book/edit";
        } catch (BookNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/publication/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("book")BookDto bookDto) {
        try {
            BookDto updatedDto = bookServices.update(bookDto);
        } catch (BookAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/book/" + bookDto.getId() +"/edit";
        }
        return "redirect:/publication/list";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id")Integer id) {
        try {
            BookDto deletedBook = bookServices.delete(id);
            System.out.println("Delete book: " + deletedBook);
        } catch (BookNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/book/" + id +"/edit";
        }
        return "redirect:/publication/list";
    }


    @GetMapping("/{idBook}/userBorrow")
    public String allUserWhoBorrowBook(@PathVariable("idBook") Integer idBook, Model model) {
        try {
            List<BookBorrowDto> borrowDtoList = bookServices.findAllUserWhoBorrowBook(idBook);
            model.addAttribute("borrows", borrowDtoList);
        }catch (BookNoAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "book/borrow";

    }
}
