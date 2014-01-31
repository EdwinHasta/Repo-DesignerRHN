/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SectoresEvaluaciones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSectoresEvaluacionesInterface {
/**
     * Método encargado de insertar un SectorEvaluacion en la base de datos.
     *
     * @param sectoresEvaluaciones SectorEvaluacion que se quiere crear.
     */
    public void crear(SectoresEvaluaciones sectoresEvaluaciones);
/**
     * Método encargado de modificar un SectorEvaluacion de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param sectoresEvaluaciones SectorEvaluacion con los cambios que se van a realizar.
     */
    public void editar(SectoresEvaluaciones sectoresEvaluaciones);

    /**
     * Método encargado de eliminar de la base de datos el SectorEvaluacion que
     * entra por parámetro.
     *
     * @param sectoresEvaluaciones SectorEvaluacion que se quiere eliminar.
     */
    public void borrar(SectoresEvaluaciones sectoresEvaluaciones);
 /**
     * Método encargado de buscar todos los SectoresEvaluaciones existentes en la base
     * de datos.
     *
     * @return Retorna una lista de SectoresEvaluaciones.
     */
    public List<SectoresEvaluaciones> consultarSectoresEvaluaciones();
/**
     * Método encargado de buscar el SectorEvaluacion con la secSectoresEvaluaciones dada
     * por parámetro.
     *
     * @param secSectorEvaluacion Secuencia del SectorEvaluacion que se quiere
     * encontrar.
     * @return Retorna el SectorEvaluacion identificado con la secSectoresEvaluaciones
     * dada por parámetro.
     */
    public SectoresEvaluaciones consultarSectorEvaluacion(BigInteger secSectorEvaluacion);
}
