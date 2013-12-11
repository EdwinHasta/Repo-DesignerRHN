/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MotivosRetiros;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosRetiros' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaMotivosRetirosInterface {
    /**
     * Método encargado de insertar un MotivoRetiro en la base de datos.
     * @param motivosRetiros MotivoRetiro que se quiere crear.
     */
    public void crear(MotivosRetiros motivosRetiros);
    /**
     * Método encargado de modificar un MotivoRetiro de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param motivosRetiros MotivoRetiro con los cambios que se van a realizar.
     */
    public void editar(MotivosRetiros motivosRetiros);
    /**
     * Método encargado de eliminar de la base de datos el MotivoRetiro que entra por parámetro.
     * @param motivosRetiros MotivoRetiro que se quiere eliminar.
     */
    public void borrar(MotivosRetiros motivosRetiros);
    /**
     * Método encargado de buscar todos los MotivosRetiros existentes en la base de datos.
     * @return Retorna una lista de MotivosRetiros.
     */
    public List<MotivosRetiros> buscarMotivosRetiros();    
    /**
     * Método encargado de buscar el MotivoRetiro con la secuencia dada por
     * parámetro.
     * @param secuencia Secuencia del MotivoRetiro que se quiere encontrar.
     * @return Retorna el MotivoRetiro identificado con la secuencia dada por
     * parámetro.
     */
    public MotivosRetiros buscarMotivoRetiroSecuencia(BigDecimal secuencia);
}
