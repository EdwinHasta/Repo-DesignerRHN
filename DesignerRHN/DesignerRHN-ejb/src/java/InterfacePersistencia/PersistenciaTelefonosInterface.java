/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Telefonos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Teléfonos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTelefonosInterface {
    /**
     * Método encargado de insertar un Teléfono en la base de datos.
     * @param telefonos Teléfono que se quiere crear.
     */
    public void crear(Telefonos telefonos);
    /**
     * Método encargado de modificar un Teléfono de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param telefonos Teléfono con los cambios que se van a realizar.
     */
    public void editar(Telefonos telefonos);
    /**
     * Método encargado de eliminar de la base de datos el Teléfono que entra por parámetro.
     * @param telefonos Teléfono que se quiere eliminar.
     */
    public void borrar(Telefonos telefonos);
    /**
     * Método encargado de buscar el Teléfono con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Teléfono que se quiere encontrar.
     * @return Retorna el Teléfono identificado con la secuencia dada por parámetro.
     */
    public Telefonos buscarTelefono(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Teléfonos existentes en la base de datos.
     * @return Retorna una lista de Teléfonos.
     */
    public List<Telefonos> buscarTelefonos();
    /**
     * Método encargado de buscar los Teléfonos de una persona.
     * @param secuenciaPersona Secuencia de la persona.
     * @return Retorna una lista de Teléfonos asociados a una persona específica.
     */
    public List<Telefonos> telefonosPersona(BigInteger secuenciaPersona);

}
