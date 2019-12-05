package app.services;


import app.dto.MagazineDto;
import app.exception.MagazineAlreadyExistsException;
import app.exception.MagazineNoExistsException;
import app.mapper.MagazineMapper;
import app.model.Magazine;
import app.repository.RepositoryMagazine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MagazineServices {

    private RepositoryMagazine repositoryMagazine;

    @Autowired
    public MagazineServices(RepositoryMagazine repositoryMagazine) {
        this.repositoryMagazine = repositoryMagazine;
    }


    public List<MagazineDto> getListMagazine() {
        return repositoryMagazine.findAll()
                .stream()
                .map(MagazineMapper::toDto)
                .collect(Collectors.toList());

    }

    public MagazineDto save(MagazineDto magazineDto) {
        Optional<Magazine> magazineByTitle = repositoryMagazine.findByTitle(magazineDto.getTitle());
        magazineByTitle.ifPresent(e -> {
            throw new MagazineAlreadyExistsException();
        });
        return mapAndSave(magazineDto);
    }

    private MagazineDto mapAndSave(MagazineDto magazineDto) {
        Magazine entity = MagazineMapper.toEntity(magazineDto);
        Magazine savedMag = repositoryMagazine.save(entity);
        return MagazineMapper.toDto(savedMag);
    }


    public MagazineDto findMagazineById(Integer id) {
        Optional<Magazine> findedMag = repositoryMagazine.findById(id);
        Magazine entity = findedMag.orElseThrow(MagazineNoExistsException::new);
        return MagazineMapper.toDto(entity);
    }

    public MagazineDto update(MagazineDto magazineDto) {
        Optional<Magazine> magByTitle = repositoryMagazine.findByTitle(magazineDto.getTitle());
        magByTitle.ifPresent(e -> {
            if(!e.getId().equals(magazineDto.getId())) {
                throw new MagazineAlreadyExistsException();
            }
        });
        return mapAndSave(magazineDto);
    }

    public MagazineDto delete(Integer id) {
        Optional<Magazine> magEntity = repositoryMagazine.findById(id);
        Magazine entity = magEntity.orElseThrow(MagazineNoExistsException::new);
        repositoryMagazine.delete(entity);
        return MagazineMapper.toDto(entity);
    }
}
