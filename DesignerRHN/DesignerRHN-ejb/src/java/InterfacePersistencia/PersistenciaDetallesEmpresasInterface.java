/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.DetallesEmpresas;
import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'DetallesEmpresas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaDetallesEmpresasInterface {
    /**
     * Método encargado de buscar el DetalleEmpresa con la secuencia dada por parámetro.
     * @param secEmpresa Secuencia de la Empresa de la cual se quiere el detalle.
     * @return Retorna el DetalleEmpresa de la emprersa identificada con la secuencia dada por parámetro. 
     */
    public DetallesEmpresas buscarDetalleEmpresa(BigInteger secEmpresa);

}
