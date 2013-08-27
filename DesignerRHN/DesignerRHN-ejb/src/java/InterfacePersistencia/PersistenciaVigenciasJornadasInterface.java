package InterfacePersistencia;

import Entidades.VigenciasJornadas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasJornadasInterface {
    
    /**
     * Crea una nueva VigenciaJornada
     * @param vigenciasJornadas Objeto a crear
     */
    public void crear(VigenciasJornadas vigenciasJornadas);
    /**
     * Modifica una VigenciaJornada
     * @param vigenciasJornadas Objeto a editar
     */
    public void editar(VigenciasJornadas vigenciasJornadas);
    /**
     * Borra una VigenciaJornada
     * @param vigenciasJornadas Objeto a borrar
     */
    public void borrar(VigenciasJornadas vigenciasJornadas);
    /**
     * Obtiene una VigenciaJornada por la llave primaria
     * @param id Llave Primaria ID
     * @return VigenciaJornada que cumple con la ID
     */
    public VigenciasJornadas buscarVigenciaJornada(Object id);
    /**
     * Obtiene la lista de VigenciaJornadas
     * @return Lista de Vigencia Jornadas
     */
    public List<VigenciasJornadas> buscarVigenciasJornadas();
    /**
     * Obtiene la lista de VigenciaJornada de un Empleado
     * @param secEmpleado Secuencia del Empleado
     * @return Lista de VigenciaJornada del Empleado
     */
    public List<VigenciasJornadas> buscarVigenciasJornadasEmpleado(BigInteger secEmpleado);
    /**
     * Obtiene una VigenciaJornada por la secuencia de esta
     * @param secVJ Secuencia VigenciaJornada
     * @return Vigencia Jornada que cumple con la secuencia
     */
    public VigenciasJornadas buscarVigenciasJornadasSecuencia(BigInteger secVJ);
    
}
