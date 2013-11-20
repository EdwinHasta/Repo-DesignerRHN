package InterfacePersistencia;

import Entidades.ParametrosEstructuras;
import java.math.BigInteger;

public interface PersistenciaParametrosEstructurasInterface {

    public ParametrosEstructuras buscarParametros();
    public BigInteger buscarEmpresaParametros(String usuarioBD);
    public ParametrosEstructuras estructurasComprobantes(String usuarioBD);
    public void editar(ParametrosEstructuras parametroEstructura);
    public void adicionarEmpleados(BigInteger secParametroEstructura);
    public Integer empleadosParametrizados(BigInteger secProceso);
}
