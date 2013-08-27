/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosLocalizaciones;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaMotivosLocalizacionesInterface {
    /**
     * Crea un nuevo MotivoLocalizaciones
     * @param motivosLocalizaciones Objeto a crear
     */
    public void crear(MotivosLocalizaciones motivosLocalizaciones);
    /**
     * Edita un MotivoLocalizacion
     * @param motivosLocalizaciones Objeto a editar 
     */
    public void editar(MotivosLocalizaciones motivosLocalizaciones);
    /**
     * Borra un MotivoLocalizacion
     * @param motivosLocalizaciones Objeto a borrar 
     */
    public void borrar(MotivosLocalizaciones motivosLocalizaciones);
    /**
     * Metodo que busca un MotivoLocalizacion por la llave primaria ID
     * @param id Llave Primaria ID
     * @return Objeto MotivosLocalizaciones que cumple con la llave primaria
     */
    public MotivosLocalizaciones buscarMotivoLocalizacion(Object id);
    /**
     * Metodo que obtiene la lista total de MotivosLocalizaciones
     * @return listML Lista de Motivos Localizaciones
     */
    public List<MotivosLocalizaciones> buscarMotivosLocalizaciones();
    /**
     * Metodo que obtiene un MotivoLocalizacion por medio de la secuencia
     * @param secuencia Secuencia del MotivoLocalizacion
     * @return motivoL MotivoLocalizacion que cumple con la secuencia
     */
    public MotivosLocalizaciones buscarMotivoLocalizacionSecuencia(BigInteger secuencia);
    
}
