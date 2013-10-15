package InterfacePersistencia;

import Entidades.VigenciasAfiliaciones;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasAfiliacionesInterface {

    /**
     * Crea una nueva VigenciasAfiliaciones
     *
     * @param vigenciasAfiliaciones Objeto a crear
     */
    public void crear(VigenciasAfiliaciones vigenciasAfiliaciones);

    /**
     * Edita una VigenciasAfiliaciones
     *
     * @param vigenciasAfiliaciones Objeto a editar
     */
    public void editar(VigenciasAfiliaciones vigenciasAfiliaciones);

    /**
     * Borra una VigenciasAfiliaciones
     *
     * @param vigenciasAfiliaciones Objeto a borrar
     */
    public void borrar(VigenciasAfiliaciones vigenciasAfiliaciones);

    /**
     * Obtiene una VigenciasAfiliaciones por la llave primaria Id
     *
     * @param id Llave Primaria Id
     * @return VigenciasAfiliaciones que cumple con la llave primaria
     */
    public VigenciasAfiliaciones buscarVigenciaAfiliacion(Object id);

    /**
     * Obtiene la lista total de VigenciasAfiliaciones
     *
     * @return Lita de VigenciasAfiliaciones
     */
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliaciones();

    /**
     * Obtiene las VigenciasAfiliaciones de un Empleado
     *
     * @param secEmpleado Secuencia Empleado
     * @return Lista VigenciasAfiliaciones de un Empleado
     */
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesEmpleado(BigInteger secEmpleado);

    /**
     * Obtiene una VigenciasAfiliaciones por su secuencia
     *
     * @param secVA Secuencia VigenciasAfiliaciones
     * @return VigenciasAfiliaciones que cumple con la secuencia
     */
    public VigenciasAfiliaciones buscarVigenciasAfiliacionesSecuencia(BigInteger secVA);

    /**
     * Obtiene la lista de VigenciasAfiliaciones de una VigenciasSueldos
     *
     * @param secVigencia Secuencia VigenciasSueldos
     * @return Lista de VigenciasAfiliaciones de una VigenciasSueldos
     */
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesVigenciaSecuencia(BigInteger secVigencia);
}
