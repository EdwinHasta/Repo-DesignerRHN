/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.NormasLaborales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'NormasLaborales' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaNormasLaboralesInterface {
    /**
     * Método encargado de insertar un NormaLaboral en la base de datos.
     * @param normasLaborales NormaLaboral que se quiere crear.
     */
    public void crear(NormasLaborales normasLaborales);
    /**
     * Método encargado de modificar una NormaLaboral de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param normasLaborales NormaLaboral con los cambios que se van a realizar.
     */
    public void editar(NormasLaborales normasLaborales);
    /**
     * Método encargado de eliminar de la base de datos la NormaLaboral que entra por parámetro.
     * @param normasLaborales NormaLaboral que se quiere eliminar.
     */
    public void borrar(NormasLaborales normasLaborales);
    /**
     * Método encargado de buscar la NormaLaboral con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la NormaLaboral que se quiere encontrar.
     * @return Retorna la NormaLaboral identificada con la secuencia dada por parámetro.
     */
    public NormasLaborales buscarNormaLaboral(BigInteger secuencia);
    /**
     * Método encargado de buscar todas las NormasLaborales existentes en la base de datos.
     * @return Retorna una lista de NormasLaborales.
     */
    public List<NormasLaborales> buscarNormasLaborales();
    /**
     * Método encargado de verificar si hay al menos una VigenciaTipoContrato asociada a una NormaLaboral.
     * @param secuencia Secuencia de la NormaLaboral
     * @return Retorna un valor mayor a cero si existe alguna VigenciaNormaEmpleado asociada a una NormaLaboral.
     */
    public Long verificarBorradoNormasLaborales(BigInteger secuencia);
}
