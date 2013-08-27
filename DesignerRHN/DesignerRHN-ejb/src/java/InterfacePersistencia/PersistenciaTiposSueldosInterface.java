/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposSueldos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaTiposSueldosInterface {
    
    /**
     * Crea un nuevo TipoSueldo
     * @param tiposSueldos Objeto a crear
     */
    public void crear(TiposSueldos tiposSueldos);
    /**
     * Edita un TipoSueldo
     * @param tiposSueldos Objeto a editar 
     */
    public void editar(TiposSueldos tiposSueldos);
    /**
     * Borra un TipoSueldo
     * @param tiposSueldos Objeto a borrar 
     */
    public void borrar(TiposSueldos tiposSueldos);
    /**
     * Obtiene un TipoSueldo por su llave primaria
     * @param id Llave Primaria Id
     * @return TipoSueldo que posee la llave primaria
     */
    public TiposSueldos buscarTipoSueldo(Object id);
    /**
     * Obtiene la lista total de TipoSueldo 
     * @return Lista de TiposSueldos
     */
    public List<TiposSueldos> buscarTiposSueldos();
    /**
     * Obtiene un TipoSueldo por su secuencia
     * @param secuencia Secuencia TipoSueldo
     * @return TipoSueldo que posee la secuencia dada
     */
    public TiposSueldos buscarTipoSueldoSecuencia(BigInteger secuencia);
    
}
