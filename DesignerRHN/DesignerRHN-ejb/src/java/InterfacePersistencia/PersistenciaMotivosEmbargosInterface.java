/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import javax.ejb.Local;
import Entidades.MotivosEmbargos;
import java.math.BigInteger;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosEmbargos' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaMotivosEmbargosInterface {
    /**
     * Método encargado de insertar un MotivoEmbargo en la base de datos.
     * @param motivosEmbargos MotivoEmbargo que se quiere crear.
     */
    public void crear(MotivosEmbargos motivosEmbargos);
    /**
     * Método encargado de modificar un MotivoEmbargo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param motivosEmbargos MotivoEmbargo con los cambios que se van a realizar.
     */
    public void editar(MotivosEmbargos motivosEmbargos);
    /**
     * Método encargado de eliminar de la base de datos el MotivoEmbargo que entra por parámetro.
     * @param motivosEmbargos MotivoEmbargo que se quiere eliminar.
     */
    public void borrar(MotivosEmbargos motivosEmbargos);
    /**
     * Método encargado de buscar el MotivoEmbargo con la secuencia dada por parámetro.
     * @param secuencia Secuencia del MotivoEmbargo que se quiere encontrar.
     * @return Retorna el MotivoEmbargo identificado con la secuencia dada por parámetro.
     */
    public MotivosEmbargos buscarMotivoEmbargo(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los MotivosEmbargos existentes en la base de datos.
     * @return Retorna una lista de MotivosEmbargos.
     */
    public List<MotivosEmbargos> buscarMotivosEmbargos();
    /**
     * Método encargado de contar los EersPrestamos que están asociados a un MotivoEmbargo específico.
     * @param secuencia Secuencia del MotivoEmbargo.
     * @return Retorna la cantidad de EersPrestamos cuyo MotivoEmbargo tiene como secuencia el valor dado por parámetro.
     */
    public BigInteger contadorEersPrestamos(BigInteger secuencia);
    /**
     * Método encargado de contar los Embargos que están asociados a un MotivoEmbargo específico.
     * @param secuencia Secuencia del MotivoEmbargo.
     * @return Retorna la cantidad de Embargos cuyo MotivoEmbargo tiene como secuencia el valor dado por parámetro.
     */
    public BigInteger contadorEmbargos(BigInteger secuencia);
}
