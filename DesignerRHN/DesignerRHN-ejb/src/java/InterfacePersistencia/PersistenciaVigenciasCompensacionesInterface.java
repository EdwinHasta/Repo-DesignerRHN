package InterfacePersistencia;

import Entidades.VigenciasCompensaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasCompensacionesInterface {
    
    /**
     * Crea una nueva VigenciasCompensaciones
     * @param vigenciasCompensaciones Objeto a crear
     */
    public void crear(VigenciasCompensaciones vigenciasCompensaciones);
    /**
     * Edita una VigenciasCompensaciones
     * @param vigenciasCompensaciones Objeto a editar
     */
    public void editar(VigenciasCompensaciones vigenciasCompensaciones);
    /**
     * Borra una VigenciasCompensaciones
     * @param vigenciasCompensaciones Borra una VigenciasCompensaciones
     */
    public void borrar(VigenciasCompensaciones vigenciasCompensaciones);
    /**
     * Obtiene una VigenciasCompensaciones por la llave primaria ID
     * @param id Llave Primaria ID
     * @return VigenciasCompensaciones que cumple con el ID
     */
    public VigenciasCompensaciones buscarVigenciaCompensacion(Object id);
    /**
     * Obtiene la lista de VigenciasCompensaciones
     * @return Lista de VigenciasCompensaciones
     */
    public List<VigenciasCompensaciones> buscarVigenciasCompensaciones();
    /**
     * Obtiene las VigenciasCompensaciones de un Empleado
     * @param secEmpleado Secuencia Empleado
     * @return VigenciasCompensaciones de un Empleado
     */
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesEmpleado(BigInteger secEmpleado);
    /**
     * Busca una VigenciasCompensaciones por su secuencia
     * @param secVC Secuencia VigenciasCompensaciones
     * @return VigenciasCompensaciones que cumple con la secuencia
     */
    public VigenciasCompensaciones buscarVigenciaCompensacionSecuencia(BigInteger secVC);
    /**
     * Obtiene las VigenciasCompensaciones de una VigenciasJornadas 
     * @param secVigencia Secuencia VigenciaJornada
     * @return VigenciasCompensaciones que cumple con la secuencia de la VigenciaJornada dada
     */
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciaSecuencia(BigInteger secVigencia);
    /**
     * Obtiene las VigenciasCompensaciones dependiendo del TipoCompensacion que se tiene
     * @param tipoC TipoCompensacion
     * @return VigenciasCompensaciones que cumple con el TipoCompensacion
     */
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesTipoCompensacion (String tipoC);
    /**
     * Metodo que obtiene la lista de VigenciasCompensaciones que cumple con la vigenciaJornada y el tipoCompensacion deseado
     * @param tipoC TipoCompensacion
     * @param secVigencia Secuencia VigenciaJornada
     * @return Lista VigenciasCompensaciones que cumple con los parametros
     */
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciayCompensacion(String tipoC,BigInteger secVigencia);
    
}
