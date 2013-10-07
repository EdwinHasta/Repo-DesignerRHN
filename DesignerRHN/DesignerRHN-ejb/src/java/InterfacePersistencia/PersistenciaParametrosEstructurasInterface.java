package InterfacePersistencia;

import Entidades.ParametrosEstructuras;
import java.math.BigInteger;

public interface PersistenciaParametrosEstructurasInterface {

    public ParametrosEstructuras buscarParametros();
    public BigInteger buscarEmpresaParametros(String usuarioBD);
    public ParametrosEstructuras estructurasComprobantes(String usuarioBD);
}
