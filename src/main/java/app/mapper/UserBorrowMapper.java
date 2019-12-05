package app.mapper;


import app.dto.UserBorrowDto;
import app.model.Borrow;
import app.model.Publication;

public class UserBorrowMapper {

    public static UserBorrowDto toDto(Borrow borrow){
        UserBorrowDto userBorrowDto = new UserBorrowDto();
        userBorrowDto.setId(borrow.getId());
        userBorrowDto.setStart(borrow.getStart());
        userBorrowDto.setEnd(borrow.getEnd());

        Publication publication = borrow.getPublication();
        userBorrowDto.setPublication_id(publication.getId());
        userBorrowDto.setPublisher(publication.getPublisher());
        userBorrowDto.setTitle(publication.getTitle());
        return userBorrowDto;
    }
}
