package cat.itb.damir.autenticaio.basic.jwt.jwt.model.serveis;


import cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats.Futbolista;
import cat.itb.damir.autenticaio.basic.jwt.jwt.model.repositoris.RepositoriFutbolista;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeiFutbolista {
    private final RepositoriFutbolista repositoriFutbolista;

    public List<Futbolista> llistarFutbolistes(){
        return repositoriFutbolista.findAll();
    }

    public Futbolista consultarPerId(Long id){
        return repositoriFutbolista.findById(id).orElse(null);
    }

    public Futbolista eliminarFutbolista(Long id){
        Futbolista res= repositoriFutbolista.findById(id).orElse(null);
        if(res!=null) repositoriFutbolista.deleteById(id);
        return res;
    }

    public Futbolista afegirFutbolista(Futbolista f){
        return repositoriFutbolista.save(f);
    }

    /** si existeix el videojoc el modifica (el torna a gravar), altrament retorna null*/
    public Futbolista modificarFutbolista(Futbolista f){
        Futbolista res=null;
        if(repositoriFutbolista.existsById(f.getId())) res= repositoriFutbolista.save(f);
        return res;
    }


}
