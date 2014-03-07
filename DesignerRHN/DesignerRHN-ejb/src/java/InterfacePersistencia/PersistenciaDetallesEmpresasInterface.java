/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.DetallesEmpresas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'DetallesEmpresas' de la base de datos.
 *
 * @author betelgeuse
 * @version 1.1 AndresPineda
 * (Crear-Editar-Borrar-BuscarDetallesEmpresas-BuscarDetalleEmpresaPorSecuencia)
 */
public interface PersistenciaDetallesEmpresasInterface {

    /**
     * Método encargado de insertar un DetalleEmpresa en la base de datos.
     *
     * @param detallesEmpresas DetalleEmpresa que se quiere crear.
     */
    public void crear(DetallesEmpresas detallesEmpresas);

    /**
     * Método encargado de modificar un DetalleEmpresa de la base de datos. Este método
     * recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param detallesEmpresas DetalleEmpresa con los cambios que se van a realizar.
     */
    public void editar(DetallesEmpresas detallesEmpresas);

    /**
     * Método encargado de eliminar de la base de datos el DetalleEmpresa que entra por
     * parámetro.
     *
     * @param detallesEmpresas DetalleEmpresa que se quiere eliminar.
     */
    public void borrar(DetallesEmpresas detallesEmpresas);

    /**
     * Método encargado de buscar todos los DetallesEmpresas existentes en la base de
     * datos.
     *
     * @return Retorna una lista de DetallesEmpresas.
     */
    public List<DetallesEmpresas> buscarDetallesEmpresas();

    /**
     * Método encargado de buscar los DetallesEmpresa de una Empresa especifica por medio de su secuencia
     * @param secEmpresa Secuencia Empresa
     * @return Retorna el DetalleEmpresa de la Empresa.
     */
    public DetallesEmpresas buscarDetalleEmpresaPorSecuencia(BigInteger secEmpresa);

    /**
     * Método encargado de buscar el DetalleEmpresa con la secuencia dada por
     * parámetro.
     *
     * @param codigoEmpresa Secuencia de la Empresa de la cual se quiere el
     * detalle.
     * @return Retorna el DetalleEmpresa de la emprersa identificada con la
     * secuencia dada por parámetro.
     */
    public DetallesEmpresas buscarDetalleEmpresa(Short codigoEmpresa);

}
