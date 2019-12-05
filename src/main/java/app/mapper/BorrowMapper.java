package app.mapper;

import app.dto.BorrowDto;
import app.model.*;

import java.util.Optional;

public class BorrowMapper {

    public static BorrowDto toDto(Borrow entity){
        BorrowDto dto = new BorrowDto();
        dto.setId(entity.getId());
        dto.setStart(entity.getStart());
        dto.setEnd(entity.getEnd());


        Publication publication =  entity.getPublication();
        User user = entity.getUser();
        dto.setPublication_id(publication.getId());
        dto.setUser_id(user.getId());

        return dto;
    }

}
