/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Motivosmvrs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosMvrs' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaMotivosMvrsInterface {
    /**
     * Método encargado de insertar un MotivoMvrs en la base de datos.
     * @param motivosMvrs MotivoMvrs que se quiere crear.
     */
    public void crear(Motivosmvrs motivosMvrs);
    /**
     * Método encargado de modificar un MotivoMvrs de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param motivosMvrs MotivoMvrs con los cambios que se van a realizar.
     */
    public void editar(Motivosmvrs motivosMvrs);
    /**
     * Método encargado de eliminar de la base de datos el MotivoMvrs que entra por parámetro.
     * @param motivosMvrs MotivoMvrs que se quiere eliminar.
     */
    public void borrar(Motivosmvrs motivosMvrs);
    /**
     * Método encargado de buscar el MotivoMvrs con la secuencia dada por parámetro.
     * @param secuencia Secuencia del MotivoMvrs que se quiere encontrar.
     * @return Retorna el MotivoMvrs identificado con la secuencia dada por parámetro.
     */
    public Motivosmvrs buscarMotivosMvrs(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los MotivosMvrs existentes en la base de datos.
     * @return Retorna una lista de MotivosMvrs.
     */
    public List<Motivosmvrs> buscarMotivosMvrs();
}
