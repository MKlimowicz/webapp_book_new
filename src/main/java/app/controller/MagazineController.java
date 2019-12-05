package app.controller;

import app.dto.BookDto;
import app.dto.MagazineDto;
import app.exception.MagazineAlreadyExistsException;
import app.exception.MagazineNoExistsException;
import app.model.Magazine;
import app.services.MagazineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magazine")
public class MagazineController {


    private MagazineServices magazineServices;

    @Autowired
    public MagazineController(MagazineServices magazineServices) {
        this.magazineServices = magazineServices;
    }


    @GetMapping("/add")
    public String addMagazine(Model model) {
        model.addAttribute("magazine", new Magazine());
        return "magazine/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("magazine")MagazineDto magazineDto) {
        try {
            System.out.println(magazineDto);
            MagazineDto savedMagazine = magazineServices.save(magazineDto);
            System.out.println("Zapisany magazyn: " + savedMagazine);
        } catch (MagazineAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/publication/list?type=magazine";
    }

    @GetMapping("/{id}/edit")
    public String editMagazine(@PathVariable("id") Integer id, Model model) {
        try {
            MagazineDto magazineDto = magazineServices.findMagazineById(id);
            model.addAttribute("magazine", magazineDto);
            return "magazine/edit";
        }catch (MagazineNoExistsException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/publication/list";
    }

    @PostMapping("/update")
    public String updateMagazine(@ModelAttribute("magazine") MagazineDto magazineDto) {
        try {
            MagazineDto dto = magazineServices.update(magazineDto);
            System.out.println("Update -> " + dto);
        }catch (MagazineAlreadyExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/book/" + magazineDto.getId() +"/edit";
        }
        return "redirect:/publication/list";
    }

    @GetMapping("/{id}/delete")
    public String deleteMagazine(@PathVariable Integer id) {
        try {
            MagazineDto magazineDto = magazineServices.delete(id);
            System.out.println("Entity deleted: " + magazineDto);
            return "redirect:/publication/list";
        } catch (MagazineNoExistsException e) {
            System.out.println(e.getMessage());
            return "redirect:/magazine/" + id + "/edit";
        }
    }
}
