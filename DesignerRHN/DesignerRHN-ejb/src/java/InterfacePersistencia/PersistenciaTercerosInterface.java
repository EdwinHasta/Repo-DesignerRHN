/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Terceros;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Terceros' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTercerosInterface {
    /**
     * Método encargado de insertar un Tercero en la base de datos.
     * @param terceros Tercero que se quiere crear.
     */
    public void crear(Terceros terceros);
    /**
     * Método encargado de modificar un Tercero de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param terceros Tercero con los cambios que se van a realizar.
     */
    public void editar(Terceros terceros);
    /**
     * Método encargado de eliminar de la base de datos el Tercero que entra por parámetro.
     * @param terceros Tercero que se quiere eliminar.
     */
    public void borrar(Terceros terceros);
    /**
     * Método encargado de buscar todos los Terceros existentes en la base de datos.
     * @return Retorna una lista de Terceros.
     */
    public List<Terceros> buscarTerceros();
    /**
     * Método encargado de buscar el Tercero con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Tercero que se quiere encontrar.
     * @return Retorna el Tercero identificado con la secuencia dada por parámetro.
     */
    public Terceros buscarTercerosSecuencia(BigInteger secuencia);
    /**
     * Método encargado de verificar si existe por lo menos un tercero con el nit que entra por parámetro. 
     * @param nit Nit del Tercero.
     * @return Retorna True si existe al menos un Tercero con el nit del parámetro, False de lo contrario.
     */
    public boolean verificarTerceroPorNit(BigInteger nit);
    /**
     * Método encargado de verficar si existe por lo menos un Tercero con el nit recibido por parámetro y que esta 
     * relacionado con la empresa cuya secuencia coincide con la recibida por parámetro.
     * @param nit Nit del Tercero.
     * @param secEmpresa Secuencia de la empresa con la que el Tercero estaría relacionado.
     * @return Retorna True si existe al menos un Tercero que cumpla las condiciones mencionadas, False de lo contrario.
     */
    public boolean verificarTerceroParaEmpresaEmpleado(BigInteger nit, BigInteger secEmpresa);
    /**
     * Método encargado de buscar todos los Terceros asociados a una empresa.
     * @param secEmpresa Secuencia de la empresa.
     * @return Retorna una lista de Terceros ordenados por nombre.
     */
    public List<Terceros> lovTerceros(BigInteger secEmpresa);
    /**
     * Método encargado de buscar todos los Terceros existentes en la base de datos
     * donde las empresa estén en la tabla Terceros
     * @return Retorna una lista de Terceros.
     */
    public List<Terceros> tercerosEmbargos();
    
    public List<Terceros> todosTerceros();
}
