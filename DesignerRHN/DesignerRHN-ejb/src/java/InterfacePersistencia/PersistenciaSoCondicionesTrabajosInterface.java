/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.SoCondicionesTrabajos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'SoCondicionTrabajos' 
 * de la base de datos.
 * @author John Pineda.
 */
@Local
public interface PersistenciaSoCondicionesTrabajosInterface {
    /**
     * Método encargado de insertar una SoCondicionTrabajo en la base de datos.
     * @param soCondicionesTrabajos SoCondicionTrabajo que se quiere crear.
     */
    public void crear(SoCondicionesTrabajos soCondicionesTrabajos);
    /**
     * Método encargado de modificar una SoCondicionTrabajo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param soCondicionesTrabajos SoCondicionTrabajo con los cambios que se van a realizar.
     */
    public void editar(SoCondicionesTrabajos soCondicionesTrabajos);
    /**
     * Método encargado de eliminar de la base de datos la SoCondicionTrabajo que entra por parámetro.
     * @param soCondicionesTrabajos SoCondicionTrabajo con los cambios que se van a realizar.
     */
    public void borrar(SoCondicionesTrabajos soCondicionesTrabajos);
    /**
     * Método encargado de buscar la SoCondicionTrabajo con la secSoCondicionTrabajo dada por parámetro.
     * @param secSoCondicionTrabajo Secuencia de la SoCondicionTrabajo que se quiere encontrar.
     * @return Retorna la SoCondicionTrabajo identificada con la secSoCondicionTrabajo dada por parámetro.
     */
    public SoCondicionesTrabajos buscarSoCondicionTrabajo(BigInteger secSoCondicionTrabajo);
    /**
     * Método encargado de buscar todas las SoCondicionesTrabajos existentes en la base de datos, ordenadas por código.
     * @return Retorna una lista de SoCondicionesTrabajos.
     */
    public List<SoCondicionesTrabajos> buscarSoCondicionesTrabajos();
    /**
     * Método encargado de recuperar cuantas Inspecciones están asociadas a una SoCondicionTrabajo específica.
     * @param secSoCondicionTrabajo Secuencia de SoCondicionTrabajo.
     * @return Retorna el número de Inspecciones cuyo atributo 'FactorRiesgo' tiene como secSoCondicionTrabajo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorInspecciones(BigInteger secSoCondicionTrabajo);
    /**
     * Método encargado de recuperar cuantos SoAccidentesMedicos están asociados a una SoCondicionTrabajo específica.
     * @param secSoCondicionTrabajo Secuencia de la SoCondicionTrabajo.
     * @return Retorna el número de SoAccidentesMedicos cuyo atributo 'FactorRiesgo' tiene como secSoCondicionTrabajo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorSoAccidentesMedicos(BigInteger secSoCondicionTrabajo);
    /**
     * Método encargado de recuperar cuantos SoDetallesPanoramas están asociados a una SoCondicionTrabajo específica.
     * @param secSoCondicionTrabajo Secuencia de la SoCondicionTrabajo.
     * @return Retorna el número de SoDetallesPanoramas cuyo atributo 'CondicionTrabajo' tiene como secSoCondicionTrabajo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorSoDetallesPanoramas(BigInteger secSoCondicionTrabajo);
    /**
     * Método encargado de recuperar cuantos SoExposicionesFr están asociados a una SoCondicionTrabajo específica.
     * @param secSoCondicionTrabajo Secuencia de la SoCondicionTrabajo.
     * @return Retorna el número de SoExposicionesFr cuyo atributo 'Indicador' tiene como secSoCondicionTrabajo el 
     * valor dado por parámetro.
     */
    public BigInteger contadorSoExposicionesFr(BigInteger secSoCondicionTrabajo);
}
