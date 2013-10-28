package InterfacePersistencia;

import Entidades.Novedades;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaNovedadesInterface {

    public List<Novedades> novedadesParaReversar(BigInteger usuarioBD, String documentoSoporte);

    public int reversarNovedades(BigInteger usuarioBD, String documentoSoporte);

    public List<Novedades> novedadesEmpleado(BigInteger secuenciaEmpleado);

    public void crear(Novedades novedades);

    public void editar(Novedades novedades);

    public void borrar(Novedades novedades);
}
