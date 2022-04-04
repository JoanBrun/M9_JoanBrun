package cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Futbolista {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String equip;
    private String lliga;
}
