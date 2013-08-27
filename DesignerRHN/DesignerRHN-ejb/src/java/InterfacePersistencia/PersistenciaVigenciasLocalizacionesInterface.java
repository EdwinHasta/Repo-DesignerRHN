/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VigenciasLocalizaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaVigenciasLocalizacionesInterface {
    
    /**
     * Crea una nueva VigenciaLocalizaciones
     * @param vigenciasLocalizaciones Objeto a crear
     */
    public void crear(VigenciasLocalizaciones vigenciasLocalizaciones);
    /**
     * Edita una VigenciaLocalizaciones
     * @param vigenciasLocalizaciones Objeto a editar
     */
    public void editar(VigenciasLocalizaciones vigenciasLocalizaciones);
    /**
     * Borra una VigenciaLocalizaciones
     * @param vigenciasLocalizaciones Objeto a borrar
     */
    public void borrar(VigenciasLocalizaciones vigenciasLocalizaciones);
    /**
     * Obtiene una VigenciaLocalizaciones por medio de la llave primaria ID
     * @param id Llave Primaria ID
     * @return VigenciaLocalizaciones que cumple con la llave primaria
     */
    public VigenciasLocalizaciones buscarVigenciasLocalizacion(Object id);
    /**
     * Obtiene el total de objetos que se encuentran en la tabla VigenciaLocalizaciones
     * @return listVL Lista de VigenciaLocalizaciones
     */
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizaciones();
    /**
     * Obtiene la lista de VigenciaLocalizaciones por un Empleado
     * @param secEmpleado Secuencia Empleado
     * @return listVLE Lista de VigenciaLocalizaciones por Empleado
     */
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizacionesEmpleado(BigInteger secEmpleado);
    /**
     * Obtiene una VigenciaLocalizaciones por medio de la secuencia
     * @param secVL Secuencia VigenciaLocalizaciones
     * @return vigenciaL VigenciaLocalizaciones que cumple con la secuencia
     */
    public VigenciasLocalizaciones buscarVigenciasLocalizacionesSecuencia(BigInteger secVL);
    
}
