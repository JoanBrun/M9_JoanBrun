package cat.itb.damir.autenticaio.basic.jwt.jwt.seguretat.jwt;

import cat.itb.damir.autenticaio.basic.jwt.jwt.model.entitats.UsuariConsultaDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuariJwt extends UsuariConsultaDTO {
    private String token;

    @Builder(builderMethodName = "jwtUsuariJwtBuilder")
    public UsuariJwt(String username, String rol, String token) {
        super(username, rol);
        this.token = token;
    }
}
