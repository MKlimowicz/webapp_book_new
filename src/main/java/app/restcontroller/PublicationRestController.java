package app.restcontroller;

import app.dto.PublicationDto;
import app.services.PublicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/publication")
public class PublicationRestController {

    private PublicationServices publicationServices;

    @Autowired
    public PublicationRestController(PublicationServices publicationServices) {
        this.publicationServices = publicationServices;
    }

    @GetMapping()
    public List<PublicationDto> getAllPublication() {
        return publicationServices.getAllPublication();
    }
}
