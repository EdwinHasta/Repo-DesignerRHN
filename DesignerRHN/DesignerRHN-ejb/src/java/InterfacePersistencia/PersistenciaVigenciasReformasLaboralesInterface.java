/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.VigenciasReformasLaborales;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'VigenciasReformasLaborales' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaVigenciasReformasLaboralesInterface {
    /**
     * Método encargado de insertar una VigenciaReformaLaboral en la base de datos.
     * @param vigenciasReformasLaborales VigenciaReformaLaboral que se quiere crear.
     */
    public void crear(VigenciasReformasLaborales vigenciasReformasLaborales);
    /**
     * Método encargado de modificar una VigenciaReformaLaboral de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param vigenciasReformasLaborales VigenciaReformaLaboral con los cambios que se van a realizar.
     */
    public void editar(VigenciasReformasLaborales vigenciasReformasLaborales);
    /**
     * Método encargado de eliminar de la base de datos la VigenciaReformaLaboral que entra por parámetro.
     * @param vigenciasReformasLaborales VigenciaReformaLaboral que se quiere eliminar.
     */
    public void borrar(VigenciasReformasLaborales vigenciasReformasLaborales);
    /**
     * Método encargado de buscar todas las VigenciasReformasLaborales existentes en la base de datos.
     * @return Retorna una lista de VigenciasReformasLaborales.
     */
    public List<VigenciasReformasLaborales> buscarVigenciasRefLab();
    /**
     * Método encargado de buscar las VigenciasReformasLaborales de un Empleado específico.
     * @param secuencia Secuencia del Empleado.
     * @return Retorna las VigenciasReformasLaborales, odenadas descendentemente por la fechaVigencia del Empleado cuya secuencia coincide 
     * con la secuencia dada por parámetro.
     */
    public List<VigenciasReformasLaborales> buscarVigenciasReformasLaboralesEmpleado(BigInteger secuencia);
    /**
     * Método encargado de buscar la VigenciaReformaLaboral con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la VigenciaReformaLaboral que se quiere encontrar.
     * @return Retorna la VigenciaReformaLaboral identificada con la secuencia dada por parámetro.
     */
    public VigenciasReformasLaborales buscarVigenciaReformaLaboralSecuencia(BigInteger secuencia);
}
