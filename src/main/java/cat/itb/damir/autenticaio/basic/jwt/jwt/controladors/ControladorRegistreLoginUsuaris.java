package cat.itb.damir.autenticaio.basic.jwt.jwt.controladors;



import cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats.Usuari;
import cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats.UsuariConsultaDTO;
import cat.itb.damir.autenticaio.basic.jwt.jwt.model.serveis.ServeiUsuari;
import cat.itb.damir.autenticaio.basic.jwt.jwt.seguretat.jwt.JwtProvider;
import cat.itb.damir.autenticaio.basic.jwt.jwt.seguretat.jwt.LoginPassword;
import cat.itb.damir.autenticaio.basic.jwt.jwt.seguretat.jwt.UsuariJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControladorRegistreLoginUsuaris {
    private final ServeiUsuari serveiUsuaris;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UsuariJwt> login(@RequestBody LoginPassword userPassword)
    {
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userPassword.getUsername(),userPassword.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        Usuari usu=(Usuari)auth.getPrincipal();
        String jwtToken = tokenProvider.generateToken(auth);
        UsuariJwt usu2=new UsuariJwt(usu.getUsername(),usu.getRol(),jwtToken);
        //es retorna userName, Avatar, Rol i Token
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usu2);
    }

    @GetMapping("/login")
    public UsuariConsultaDTO login(@AuthenticationPrincipal Usuari usu){
        UsuariConsultaDTO usu2 = new UsuariConsultaDTO(usu.getUsername(),usu.getRol());
           return usu2;
    }

    @PostMapping("/usuaris")
    public ResponseEntity<?> nouUsuari(@RequestBody Usuari nouUsuari) {
        try {
            Usuari res = serveiUsuaris.crearNouUsuari(nouUsuari);
            UsuariConsultaDTO usu = new UsuariConsultaDTO(res.getUsername(), res.getRol());
            return new ResponseEntity<UsuariConsultaDTO>(usu, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/usuaris")
    public ResponseEntity<?> llistarUsuarisDTO() {
        List<Usuari> res = serveiUsuaris.llistarUsuaris();
        List<UsuariConsultaDTO> aux = new ArrayList();
        for (Usuari usu : res) {
            aux.add(new UsuariConsultaDTO(usu.getUsername(), usu.getRol()));
        }
        if (res.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(aux);
    }
}
