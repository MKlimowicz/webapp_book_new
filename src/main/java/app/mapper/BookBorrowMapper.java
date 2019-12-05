package app.mapper;

import app.dto.BookBorrowDto;
import app.model.Borrow;
import app.model.User;

public class BookBorrowMapper {

    public static BookBorrowDto toDto(Borrow borrow) {
        BookBorrowDto dto = new BookBorrowDto();
        dto.setId(borrow.getId());
        dto.setEnd(borrow.getEnd());
        dto.setStart(borrow.getStart());

        User user = borrow.getUser();
        dto.setIdUser(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPesel(user.getPesel());

        return dto;
    }
}
