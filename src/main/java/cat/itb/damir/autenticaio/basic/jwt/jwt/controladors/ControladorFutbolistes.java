package cat.itb.damir.autenticaio.basic.jwt.jwt.controladors;


import cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats.Futbolista;
import cat.itb.damir.autenticaio.basic.jwt.jwt.model.serveis.ServeiFutbolista;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorFutbolistes {
    private final ServeiFutbolista serveiFutbolistes;

    @GetMapping("/futbolistes")
    public ResponseEntity<?> consultarFutbolistes() {
        List<Futbolista> res = serveiFutbolistes.llistarFutbolistes();
        if (res != null) return ResponseEntity.ok(res);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/futbolistes")
    public ResponseEntity<?> afegirFutbolista(@RequestBody Futbolista f) {
        try {
            serveiFutbolistes.afegirFutbolista(f);
            return new ResponseEntity<Futbolista>(f, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/futbolistes/{id}")
    public ResponseEntity<?> consultarUnFutbolista(@PathVariable long id) {
        Futbolista f = serveiFutbolistes.consultarPerId(id);
        if (f != null) {
            return ResponseEntity.ok(f);
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping("/futbolistes")
    public ResponseEntity<?> modificarFutbolista(@RequestBody Futbolista fmod){
        Futbolista res=serveiFutbolistes.modificarFutbolista(fmod);
        if(res!=null) return ResponseEntity.ok(res);
        else return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/futbolistes/{id}")
    public ResponseEntity<?> eliminarFutbolista(@PathVariable long id){
        Futbolista res=serveiFutbolistes.eliminarFutbolista(id);
        if(res!=null){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.notFound().build();
    }

}
