/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Operandos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Operandos' de la base de datos.
 *
 * @author Andres Pineda.
 */
public interface PersistenciaOperandosInterface {

    /**
     * Método encargado de buscar todos los Operandos existentes en la base de
     * datos.
     *
     * @return Retorna una lista de Operandos.
     */
    public List<Operandos> buscarOperandos();

    public void crear(Operandos operandos);

    public void editar(Operandos operandos);
    
    public void borrar(Operandos operandos);
    
    public String valores(BigInteger secuenciaOperando) ;
    
    public Operandos operandosPorSecuencia(BigInteger secuencia);
}
