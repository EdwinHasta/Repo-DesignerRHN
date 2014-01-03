/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposChequeos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposChequeos' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaTiposChequeosInterface {
    /**
     * Método encargado de insertar un TipoChequeo en la base de datos.
     * @param tiposChequeos TipoChequeo que se quiere crear.
     */
    public void crear(TiposChequeos tiposChequeos);
    /**
     * Método encargado de modificar un TipoChequeo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposChequeos TipoChequeo con los cambios que se van a realizar.
     */
    public void editar(TiposChequeos tiposChequeos);
    /**
     * Método encargado de eliminar de la base de datos el TipoChequeo que entra por parámetro.
     * @param tiposChequeos TipoChequeo con los cambios que se van a realizar.
     */
    public void borrar(TiposChequeos tiposChequeos);
    /**
     * Método encargado de buscar el TipoChequeo con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoChequeo que se quiere encontrar.
     * @return Retorna el TipoChequeo identificada con la secuencia dada por parámetro.
     */
    public TiposChequeos buscarTipoChequeo(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los TiposChequeos existentes en la base de datos, ordenadas por código.
     * @return Retorna una lista de TiposChequeos.
     */
    public List<TiposChequeos> buscarTiposChequeos();
    /**
     * Método encargado de recuperar cuantos ChequeosMedicos están asociados a un TipoChequeo específico.
     * @param secuencia Secuencia de TipoChequeo.
     * @return Retorna el número de ChequeosMedicos cuyo atributo 'TipoChequeo' tiene como secuencia el 
     * valor dado por parámetro.
     */
    public BigInteger contadorChequeosMedicos(BigInteger secuencia);
    /**
     * Método encargado de recuperar cuantos TiposExamenesCargos están asociados a un TipoChequeo específico.
     * @param secuencia Secuencia de TipoChequeo.
     * @return Retorna el número de TiposExamenesCargos cuyo atributo 'TipoChequeo' tiene como secuencia el 
     * valor dado por parámetro.
     */
    public BigInteger contadorTiposExamenesCargos(BigInteger secuencia);
}
