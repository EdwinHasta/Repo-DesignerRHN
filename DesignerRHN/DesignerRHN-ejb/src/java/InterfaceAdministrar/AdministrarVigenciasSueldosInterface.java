package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.MotivosCambiosSueldos;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasSueldos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */

public interface AdministrarVigenciasSueldosInterface {
    
    /**
     * Obtiene las VigenciasSueldos de un Empleado
     * @param secEmpleado Secuencia Empleado
     * @return Lista de VigenciasSueldos del empleado en cuestion
     */
    public List<VigenciasSueldos> VigenciasSueldosEmpleado(BigInteger secEmpleado);
    /**
     * Modifica la lista de VigenciasSueldos
     * @param listVSModificadas Lista de VigenciasSueldos para modificar
     */
    public void modificarVS(List<VigenciasSueldos> listVSModificadas);
    /**
     * Borra una VigenciasSueldos
     * @param vigenciasSueldos Objeto a borrar
     */
    public void borrarVS(VigenciasSueldos vigenciasSueldos);
    /**
     * Crea una nueva VigenciasSueldos
     * @param vigenciasSueldos Objeto a crear
     */
    public void crearVS(VigenciasSueldos vigenciasSueldos);
    /**
     * Busca un Empleado por su secuencia
     * @param secuencia Secuencia Empleado
     * @return Empleado que cumple con la secuencia dada
     */
    public Empleados buscarEmpleado(BigInteger secuencia);
    /**
     * Obtiene la lista de VigenciasAfiliaciones de una VigenciasSueldos
     * @param secVigencia Secuencia VigenciasSueldos
     * @return Lista de VigenciasAfiliaciones de una VigenciasSueldos
     */
    public List<VigenciasAfiliaciones> VigenciasAfiliacionesVigencia(BigDecimal secVigencia);
    /**
     * Modifica una lista de VigenciasAfiliaciones
     * @param listVAModificadas Lista de VigenciasAfiliaciones a ser modificada
     */
    public void modificarVA(List<VigenciasAfiliaciones> listVAModificadas);
    /**
     * Borra una VigenciasAfiliaciones
     * @param vigenciasAfiliaciones Objeto a borrar
     */
    public void borrarVA(VigenciasAfiliaciones vigenciasAfiliaciones);
    /**
     * Crea una nueva VigenciasAfiliaciones 
     * @param vigenciasAfiliaciones Objeto a crear
     */
    public void crearVA(VigenciasAfiliaciones vigenciasAfiliaciones);
    /**
     * Obtiene la lista de TiposSueldos
     * @return Lista de TiposSueldos
     */
    public List<TiposSueldos> tiposSueldos();
    /**
     * Obtiene la lista de MotivosCambiosSueldos
     * @return Lista de MotivosCambiosSueldos
     */
    public List<MotivosCambiosSueldos> motivosCambiosSueldos();
    /**
     * Obtiene la lista de TiposEntidades
     * @return Lista de TiposEntidades
     */
    public List<TiposEntidades> tiposEntidades();
    /**
     * Obtiene la lista de Terceros
     * @return Lista de Terceros
     */
    public List<Terceros> terceros();
    /**
     * Obtiene la lista de TercerosSucursales
     * @return Lista de TercerosSucursales
     */
    public List<TercerosSucursales> tercerosSucursales();
    /**
     * Crea una neva TerceraSucursal
     * @param tercerosSucursales Objeto a crear
     */
    public void crearTerceroSurcursal(TercerosSucursales tercerosSucursales);
    /**
     * Cierra la sesion
     */
    public void salir();
    /**
     * Obtiene las VigenciasSueldos Actuales de un Empleado
     * @param secEmpleado Secuencia Empleado
     * @return Lista de VigenciasSueldos Actuales del empleado en cuestion
     */
    public List<VigenciasSueldos> VigenciasSueldosActualesEmpleado(BigInteger secEmpleado);
    
}
