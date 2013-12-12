/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Cuentas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Cuentas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaCuentasInterface {
    /**
     * Método encargado de insertar una Cuenta en la base de datos.
     * @param cuentas Cuenta que se quiere crear.
     */
    public void crear(Cuentas cuentas);
    /**
     * Método encargado de modificar una Cuenta de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param cuentas Cuenta con los cambios que se van a realizar.
     */
    public void editar(Cuentas cuentas);
    /**
     * Método encargado de eliminar de la base de datos la cuenta que entra por parámetro.
     * @param cuentas Cuenta que se quiere eliminar.
     */
    public void borrar(Cuentas cuentas);
    /**
     * Método encargado de buscar todas las cuentas existentes en la base de datos.
     * @return Retorna una lista de Cuentas.
     */
    public List<Cuentas> buscarCuentas();
    /**
     * Método encargado de buscar la Cuenta con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la Cuenta que se quiere encontrar.
     * @return Retorna la Cuenta identificada con la secuencia dada por parámetro.
     */
    public Cuentas buscarCuentasSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar las Cuentas de una empresa especifica.
     * @param secuencia Secuencia de la empresa a quien pertenecen las cuentas.
     * @return Retorna una lista de Cuentas, las cuales pertenecen a una empresa
     */
    public List<Cuentas> buscarCuentasSecuenciaEmpresa(BigInteger secuencia);
}
