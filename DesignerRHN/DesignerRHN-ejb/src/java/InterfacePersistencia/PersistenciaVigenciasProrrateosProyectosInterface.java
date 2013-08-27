package InterfacePersistencia;

import Entidades.VigenciasProrrateosProyectos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasProrrateosProyectosInterface {
    
    /**
     * Crea una nueva VigenciasProrrateosProyectos
     * @param vigenciasProrrateosProyectos Objeto a crear
     */
    public void crear(VigenciasProrrateosProyectos vigenciasProrrateosProyectos);
    /**
     * Edita una VigenciasProrrateosProyectos
     * @param vigenciasProrrateosProyectos Objeto a editar
     */
    public void editar(VigenciasProrrateosProyectos vigenciasProrrateosProyectos);
    /**
     * Borra una VigenciasProrrateosProyectos
     * @param vigenciasProrrateosProyectos Objeto a borrar
     */
    public void borrar(VigenciasProrrateosProyectos vigenciasProrrateosProyectos);
    /**
     * Busca una VigenciasProrrateosProyectos por su llave primaria ID
     * @param id Llave Primaria Id
     * @return vPP VigenciasProrrateosProyectos que cumple con la llave primaria 
     */
    public VigenciasProrrateosProyectos buscarVigenciaProrrateoProyecto(Object id);
    /**
     * Obtiene la lista total de VigenciasProrrateosProyectos 
     * @return listVPP Lista de VigenciasProrrateosProyectos existente
     */
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectos();
    /**
     * Busca la lista de VigenciasProrrateosProyectos de un Empleado por medio de la secuencia
     * @param secEmpleado Secuencia Empleado
     * @return listVPPE Lista de VigenciasProrrateosProyectos de el Empleado deseado
     */
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectosEmpleado(BigInteger secEmpleado);
    /**
     * Busca una VigenciasProrrateosProyectos por medio de la secuencia de la vigtencia
     * @param secVPP Secuencia VigenciasProrrateosProyectos
     * @return vPP VigenciasProrrateosProyectos que cumple con la secuencia dada
     */
    public VigenciasProrrateosProyectos buscarVigenciasProrrateosProyectosSecuencia(BigInteger secVPP);
    /**
     * Obtiene de VigenciasProrrateosProyectos la lista de VigenciaLocalizaciones por medio de la secuencia de esta
     * @param secVigencia Secuencia VigenciaLocalizacion
     * @return listVPPVL Lista de VigenciasProrrateosProyectos de una VigenciaLocalizacion
     */
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectosVigenciaSecuencia(BigInteger secVigencia);
    
}
