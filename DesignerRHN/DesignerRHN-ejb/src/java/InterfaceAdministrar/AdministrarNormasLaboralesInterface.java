/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.NormasLaborales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarNormasLaboralesInterface {

    /**
     * Método encargado de modificar NormasLaborales.
     *
     * @param listaNormasLaborales Lista NormasLaborales que se van a modificar.
     */
    public void modificarNormasLaborales(List<NormasLaborales> listaNormasLaborales);

    /**
     * Método encargado de borrar NormasLaborales.
     *
     * @param listaNormasLaborales Lista NormasLaborales que se van a borrar.
     */
    public void borrarNormasLaborales(List<NormasLaborales> listaNormasLaborales);

    /**
     * Método encargado de crear NormasLaborales.
     *
     * @param listaNormasLaborales Lista NormasLaborales que se van a crear.
     */
    public void crearNormasLaborales(List<NormasLaborales> listaNormasLaborales);

    /**
     * Método encargado de recuperar las NormasLaborales para una tabla de la
     * pantalla.
     *
     * @return Retorna una lista de NormasLaborales.
     */
    public List<NormasLaborales> mostrarNormasLaborales();

    /**
     * Método encargado de recuperar una NormasLaborales dada su secuencia.
     *
     * @param secNormasLaborales Secuencia del NormasLaborales
     * @return Retorna una NormasLaborales.
     */
    public NormasLaborales mostrarMotivoContrato(BigInteger secNormasLaborales);

    /**
     * Método encargado de consultar si existe una relacion entre una
     * NormasLaborales específica y algún VigenciasNormasEmpleado. Adémas de la
     * revisión, establece cuantas relaciones existen.
     *
     * @param secNormasLaborales Secuencia del NormasLaborales.
     * @return Retorna el número de VigenciasNormasEmpleado relacionados con la
     * MotivoCambioCargo cuya secuencia coincide con el parámetro.
     */
    public BigInteger verificarVigenciasNormasEmpleadoNormaLaboral(BigInteger secNormasLaborales);
}
