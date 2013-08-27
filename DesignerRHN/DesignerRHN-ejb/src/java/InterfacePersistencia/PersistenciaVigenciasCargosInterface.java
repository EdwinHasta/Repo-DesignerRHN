
package InterfacePersistencia;

import Entidades.VigenciasCargos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaVigenciasCargosInterface {
 
    public void crear(VigenciasCargos vigenciasCargos);
    public void editar(VigenciasCargos vigenciasCargos);
    public void borrar(VigenciasCargos vigenciasCargos);
    public VigenciasCargos buscarVigenciaCargo(Object id);
    public List<VigenciasCargos> buscarVigenciasCargos();
    public VigenciasCargos buscarCargoEmpleado (BigDecimal secuencia);
    public List<VigenciasCargos> buscarVigenciaCargoEmpleado(BigInteger secEmpleado);

}
