/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.NovedadesSistema;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'NovedadesSistema' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaNovedadesSistemaInterface {
    /**
     * Método encargado de insertar una NovedadSistema en la base de datos.
     * @param novedades NovedadSistema que se quiere crear.
     */
    public void crear(NovedadesSistema novedades);
    /**
     * Método encargado de modificar una NovedadSistema de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param novedades NovedadSistema con los cambios que se van a realizar.
     */
    public void editar(NovedadesSistema novedades);
    /**
     * Método encargado de eliminar de la base de datos la NovedadSistema que entra por parámetro.
     * @param novedades NovedadSistema que se quiere eliminar.
     */
    public void borrar(NovedadesSistema novedades);
    /**
     * Método encargado de buscar las NovedadesSistema ordenadas por fechaInicialDisfrute,
     * de tipo 'DEFINITIVA' y de un empleado dado como parámetro.
     * @param secuenciaEmpleado Secuencia del empleado al que se la buscan las NovedadesSistema
     * @return Retorna una lista de NovedadesSistema.
     */
    public List<NovedadesSistema> novedadesEmpleado(BigInteger secuenciaEmpleado);
    
    public List<NovedadesSistema> novedadesEmpleadoVacaciones(BigInteger secuenciaEmpleado);
    
    }
