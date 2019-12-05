package app.services;

import app.dto.BookDto;
import app.dto.MagazineDto;
import app.dto.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicationServices {

    private BookServices bookServices;
    private MagazineServices magazineServices;

    @Autowired
    public PublicationServices(BookServices bookServices, MagazineServices magazineServices) {
        this.bookServices = bookServices;
        this.magazineServices = magazineServices;
    }


    public List<PublicationDto> getAllPublication() {
        List<PublicationDto> publicationDtoList = new ArrayList<>();

        List<BookDto> listBook = bookServices.getListBook();
        List<MagazineDto> listMagazine = magazineServices.getListMagazine();

        publicationDtoList.addAll(listBook);
        publicationDtoList.addAll(listMagazine);


        return publicationDtoList;
    }

    public List<PublicationDto> getAllMagazine() {
     return new ArrayList<>(magazineServices.getListMagazine());
    }

    public List<PublicationDto> getAllBook() {
        return new ArrayList<>(bookServices.getListBook());
    }


}
