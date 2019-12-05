package app.restcontroller;

import app.dto.MagazineDto;
import app.services.MagazineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/magazine")
public class MagazineRestController {

    private MagazineServices magazineService;

    @Autowired
    public MagazineRestController(MagazineServices magazineService) {
        this.magazineService = magazineService;
    }


    @GetMapping()
    public List<MagazineDto> getAllMagazine() {
        return magazineService.getListMagazine();
    }

    @PostMapping()
    public ResponseEntity<MagazineDto> save(@RequestBody MagazineDto dto) {
        if(dto.getId() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Magazyn kóry próbujesz zapisać nie może mieć ustawionego id");
        MagazineDto savedDto = magazineService.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MagazineDto> update(@PathVariable("id")Integer id, @RequestBody MagazineDto dto) {
        if(!id.equals(dto.getId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id w ścieżce musi byc takie samo jak id magazyny");
        MagazineDto updatedMag = magazineService.update(dto);
        return ResponseEntity.ok(updatedMag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MagazineDto> delete(@PathVariable("id") Integer id) {
        if(id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id magazynu ktore chcesz usunac musi byc rozny od null");
        MagazineDto deleted = magazineService.delete(id);
        return ResponseEntity.ok(deleted);
    }
}
