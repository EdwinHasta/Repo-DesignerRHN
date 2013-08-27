package InterfacePersistencia;

import Entidades.VigenciasSueldos;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaVigenciasSueldosInterface {

    public void crear(VigenciasSueldos vigenciasSueldos);

    public void editar(VigenciasSueldos vigenciasSueldos);

    public void borrar(VigenciasSueldos vigenciasSueldos);

    public VigenciasSueldos buscarVigenciaSueldo(Object id);

    public List<VigenciasSueldos> buscarVigenciasSeldos();

    /**
     * Obtiene las VigenciasSueldos de un Empleado
     *
     * @param secEmpleado Secuencia Empleado
     * @return Lista de VigenciasSueldos de un Empleado
     */
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleado(BigInteger secEmpleado);

    /**
     * Obtiene una VigenciasSueldos por su secuencia
     *
     * @param secVC Secuencia VigenciasSueldos
     * @return VigenciasSueldos que cumple con la secuencia
     */
    public VigenciasSueldos buscarVigenciasSueldosSecuencia(BigInteger secVS);

    /**
     * Obtiene las VigenciasSueldos Actuales de un Empleado
     *
     * @param secEmpleado Secuencia Empleado
     * @return Lista de VigenciasSueldos Actuales de un Empleado
     */
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleadoRecientes(BigInteger secEmpleado);
}
