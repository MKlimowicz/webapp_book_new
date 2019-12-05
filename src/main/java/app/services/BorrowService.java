package app.services;

import app.dto.BorrowDto;
import app.exception.BorrowNoExistsException;
import app.exception.InvalidBorrowPublication;
import app.mapper.BorrowMapper;
import app.model.*;
import app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowService {

    private BorrowRepository borrowRepository;
    private PublicationRepository publicationRepository;
    private RepositoryUser userRepository;


    @Autowired
    public BorrowService(BorrowRepository borrowRepository, PublicationRepository publicationRepository, RepositoryUser userRepository) {
        this.borrowRepository = borrowRepository;
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;

    }

    public BorrowDto saveBorrow(Integer id_user, Integer id_publication) {
        isPublicationAlreadyBorrow(id_publication);
        User user = getUserById(id_user);
        Publication publication = getPublicationById(id_publication);
        return mapAndSaveBorrow(user, publication);
    }





    private void isPublicationAlreadyBorrow(Integer idPublication) {
        Optional<Borrow> findedPublication = borrowRepository.findByPublication_idAndEndIsNull(idPublication);
        findedPublication.ifPresent((e) -> {
            throw new InvalidBorrowPublication("Ksiazka  o id: " + idPublication + ". Jest juz wypożyczona");
        });
    }



    private Publication getPublicationById(Integer publication_id) {
        Optional<Publication> publicationById = publicationRepository.findById(publication_id);
        return publicationById.orElseThrow(() -> {
            throw new InvalidBorrowPublication("Ksiazka o id: " + publication_id +  ". Nie istnieje");
        });
    }


    private User getUserById(Integer id_user) {
        Optional<User> userById = userRepository.findById(id_user);
        return userById.orElseThrow(() -> {
            throw new InvalidBorrowPublication("Uzytkownik o id: " + id_user + "nie istnieje");
        });
    }

    private BorrowDto mapAndSaveBorrow(User user, Publication publication) {
        Borrow borrow = new Borrow();
        borrow.setUser(user);
        borrow.setPublication(publication);
        borrow.setStart(LocalDate.now());

        Borrow savedBorrow = borrowRepository.save(borrow);

        return BorrowMapper.toDto(savedBorrow);
    }


    @Transactional
    public LocalDate returnBorrow(Integer id) {
        Optional<Borrow> borrowById = borrowRepository.findById(id);
        Borrow borrow = borrowById.orElseThrow(BorrowNoExistsException::new);
        if(borrow.getEnd() != null) {
            throw new BorrowNoExistsException();
        }

        borrow.setEnd(LocalDate.now());
        return borrow.getEnd();
    }
}
