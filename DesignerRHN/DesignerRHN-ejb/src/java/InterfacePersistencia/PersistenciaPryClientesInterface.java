/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.PryClientes;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'PryClientes' 
 * de la base de datos.
 * @author Viktor
 */
public interface PersistenciaPryClientesInterface {
    /**
     * Método encargado de buscar todos los PryClientes existentes en la base de datos, ordenados por nombre.
     * @return Retorna una lista de PryClientes ordenados por nombre.
     */
    public List<PryClientes> pryclientes();
    
}
