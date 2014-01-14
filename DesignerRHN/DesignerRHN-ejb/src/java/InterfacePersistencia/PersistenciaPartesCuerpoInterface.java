/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.PartesCuerpo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'ParteCuerpos' 
 * de la base de datos.
 * @author John Pineda.
 */
@Local
public interface PersistenciaPartesCuerpoInterface {
    /**
     * Método encargado de insertar una ParteCuerpo en la base de datos.
     * @param partesCuerpo ParteCuerpo que se quiere crear.
     */
    public void crear(PartesCuerpo partesCuerpo);
    /**
     * Método encargado de modificar una ParteCuerpo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param partesCuerpo ParteCuerpo con los cambios que se van a realizar.
     */
    public void editar(PartesCuerpo partesCuerpo);
    /**
     * Método encargado de eliminar de la base de datos la ParteCuerpo que entra por parámetro.
     * @param partesCuerpo ParteCuerpo con los cambios que se van a realizar.
     */
    public void borrar(PartesCuerpo partesCuerpo);
    /**
     * Método encargado de buscar la ParteCuerpo con la secPartesCuerpo dada por parámetro.
     * @param secPartesCuerpo Secuencia de la ParteCuerpo que se quiere encontrar.
     * @return Retorna la ParteCuerpo identificada con la secPartesCuerpo dada por parámetro.
     */
    public PartesCuerpo buscarParteCuerpo(BigInteger secPartesCuerpo);
    /**
     * Método encargado de buscar todas las PartesCuerpo existentes en la base de datos, ordenadas por código.
     * @return Retorna una lista de PartesCuerpo.
     */
    public List<PartesCuerpo> buscarPartesCuerpo();
    /**
     * Método encargado de recuperar cuantos SoAccidentesMedicos estan asociados a una ParteCuerpo específica.
     * @param secPartesCuerpo Secuencia de la ParteCuerpo.
     * @return Retorna el numero de SoAccidentesMedicos cuyo atributo 'Parte' tiene como secPartesCuerpo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorSoAccidentesMedicos(BigInteger secPartesCuerpo);
    /**
     * Método encargado de recuperar cuantos SoDetallesExamenes estan asociados a una ParteCuerpo específica.
     * @param secPartesCuerpo Secuencia de la ParteCuerpo.
     * @return Retorna el numero de SoDetallesExamenes cuyo atributo 'ParteCuerpo' tiene como secPartesCuerpo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorDetallesExamenes(BigInteger secPartesCuerpo);
    /**
     * Método encargado de recuperar cuantos SoDetallesRevisiones están asociados a una ParteCuerpo específica.
     * @param secPartesCuerpo Secuencia de la ParteCuerpo.
     * @return Retorna el número de SoDetallesRevisiones cuyo atributo 'Organo' tiene como secPartesCuerpo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorSoDetallesRevisiones(BigInteger secPartesCuerpo);
}
