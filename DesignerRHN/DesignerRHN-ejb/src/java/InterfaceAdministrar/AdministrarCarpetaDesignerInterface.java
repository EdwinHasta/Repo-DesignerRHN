package InterfaceAdministrar;

import Entidades.Aficiones;
import Entidades.Modulos;
import Entidades.Pantallas;
import Entidades.Tablas;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarCarpetaDesignerInterface {

    public List<Modulos> ConsultarModulos();

    public List<Tablas> ConsultarTablas(BigInteger secuenciaMod);

    public Pantallas ConsultarPantalla(BigInteger secuenciaTab);

    public List<Aficiones> buscarAficiones();

    public Aficiones unaAficion(BigInteger id);
    //public Aficiones buscarAfi(BigDecimal cod);

    public List<Aficiones> buscarAfi();

    public void modificarAficion(List<Aficiones> listAficiones);

    public Integer sugerenciaCodigoAficiones();

    public void crearAficion(Aficiones aficion);

    public void borrarAficion(Aficiones aficion);

    public Aficiones buscarAfiCodigo(Short cod);
}
