/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Conceptos;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Conceptos' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaConceptosInterface {

    /**
     * Método encargado de insertar un Concepto en la base de datos.
     *
     * @param concepto Concepto que se quiere crear.
     */
    public void crear(Conceptos concepto);

    /**
     * Método encargado de modificar un Concepto de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param concepto Concepto con los cambios que se van a realizar.
     */
    public void editar(Conceptos concepto);

    /**
     * Método encargado de eliminar de la base de datos el Concepto que entra
     * por parámetro.
     *
     * @param concepto Concepto que se quiere eliminar.
     */
    public void borrar(Conceptos concepto);

    /**
     * Método encargado de buscar todos los Conceptos existentes en la base de
     * datos.
     *
     * @return Retorna una lista de Conceptos.
     */
    public List<Conceptos> buscarConceptos();

    /**
     * Método encargado de validar que el código que entra por parámetro existe
     * en la base de datos.
     *
     * @param codigoConcepto Código que se quiere revisar si esta o no en la
     * base de datos
     * @return True si el Código esta en la base de datos
     */
    public boolean verificarCodigoConcepto(BigInteger codigoConcepto);

    /**
     * Método encargado de buscar los conceptos cuyo código existe y pertencen a
     * una empresa especifica.
     *
     * @param codigoConcepto Código del concepto que se quiere buscar.
     * @param secEmpresa Secuencia de la empresa a la que pertenece el concepto
     * @return Retorna la lista de Conceptos cuyo código concuerda con el dado y
     * que pertenecen a la empresa con secuencia igual a la dada por parámetro.
     */
    public Conceptos validarCodigoConcepto(BigInteger codigoConcepto, BigInteger secEmpresa);

    /**
     * Método encargado de buscar los Conceptos de una empresa especifica.
     *
     * @param secEmpresa Secuencia de la empresa a la que pertenecen los
     * conceptos.
     * @return Retorna una lista de los conceptos de le empresa cuya secuencia
     * es igual a la que entra por parámetro.
     */
    public List<Conceptos> conceptosPorEmpresa(BigInteger secEmpresa);

    /**
     * Método encargado de buscar los Conceptos de una empresa especifica y
     * filtra entre conceptos activos e inactivos.
     *
     * @param secEmpresa Secuencia de la empresa a la que pertenecen los
     * conceptos.
     * @param estado Estado de los conceptos que se quieren buscar. 'ACTIVO' o
     * 'INACTIVO'
     * @return Retorna una lista con los conceptos que cumplen con el estado
     * dado por parámetro.
     */
    public List<Conceptos> conceptosEmpresaActivos_Inactivos(BigInteger secEmpresa, String estado);

    /**
     * Método encargado de consultar todos los conceptos de la empresa menos los
     * conceptos pasivos. Un concepto pasivo es un estimado de cuanto le cuesta
     * a una empresa un empleado.
     *
     * @param secEmpresa Secuencia de la empresa a la que pertenecen los
     * conceptos.
     * @return Retorna una lista de conceptos No pasivos que pertenecen a una
     * empresa especifica.
     */
    public List<Conceptos> conceptosEmpresaSinPasivos(BigInteger secEmpresa);

    /**
     * Método encargado de clonar un concepto. Se crea un nuevo concepto con los
     * datos de un concepto base. Se le puede cambiar el código y la descripcion
     * al nuevo concepto.
     *
     * @param secConceptoOrigen Secuencia del concepto que se va a clonar.
     * @param codigoConceptoNuevo Código del nuevo concepto.
     * @param descripcionConceptoNuevo Descripción del nuevo concepto.
     */
    public void clonarConcepto(BigInteger secConceptoOrigen, BigInteger codigoConceptoNuevo, String descripcionConceptoNuevo);

    /**
     * Método encargado de buscar el Contrato con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia del Contrato que se quiere encontrar.
     * @return Retorna el Contrato identificado con la secuencia dada por
     * parámetro.
     */
    public Conceptos conceptosPorSecuencia(BigInteger secuencia);
    /**
     * Elimina un concepto y todos los datos asociados a este.
     * @param secuenciaConcepto Secuencia del concepto que se va a eliminar
     * @return Retorna True si fue posible eliminar el concepto, false de lo contrario.
     */
    public boolean eliminarConcepto(BigInteger secuenciaConcepto);

    public String conceptoParaFormulaContrato(BigInteger secuencia, Date fechaFin);

}
