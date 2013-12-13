/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Soaccidentes;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Soaccidentes' 
 * de la base de datos.
 * @author Viktor
 */
public interface PersistenciaSoaccidentesInterface {
    /**
     * Método encargado de insertar un Soaccidente en la base de datos.
     * @param soaccidentes Soaccidente que se quiere crear.
     */
    public void crear(Soaccidentes soaccidentes);
    /**
     * Método encargado de modificar un Soaccidente de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param soaccidentes Soaccidente con los cambios que se van a realizar.
     */
    public void editar(Soaccidentes soaccidentes);
    /**
     * Método encargado de eliminar de la base de datos el Soaccidente que entra por parámetro.
     * @param soaccidentes Soaccidente que se quiere eliminar.
     */
    public void borrar(Soaccidentes soaccidentes);
    /**
     * Método encargado de buscar los Soaccidentes (accidentes) asociados a un empleado específico.
     * @param secuenciaEmpleado Secuencia del empleado para el cual se quieren saber los Soaccidentes.
     * @return Retorna una lista de Soaccidentes.
     */
    public List<Soaccidentes> accidentesEmpleado(BigInteger secuenciaEmpleado);
}
