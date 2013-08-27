/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Proyectos;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaProyectosInterface {
    
    /**
     * Crea un proyecto
     * @param proyectos Objeto a crear 
     */
    public void crear(Proyectos proyectos);
    /**
     * Edita un proyecto
     * @param proyectos Objeto a editar
     */
    public void editar(Proyectos proyectos);
    /**
     * Borrar un proyecto
     * @param proyectos Objeto a borrar
     */
    public void borrar(Proyectos proyectos);
    /**
     * Obtiene un proyecto por medio de la llave primaria ID
     * @param id Llave Primaria ID
     * @return Objeto Proyecto que cumple con la llave primaria
     */
    public Proyectos buscarProyecto(Object id);
    /**
     * Obtiene el total de elementos de la tabla proyectos
     * @return listP Lista de Proyectos
     */
    public List<Proyectos> buscarProyectos();
    /**
     * Obtiene un proyecto por medio de la secuencia
     * @param secuencia Secuencia del proyecto
     * @return proyecto Proyecto que cumple con la secuencia dada
     */
    public Proyectos buscarProyectoSecuencia(BigDecimal secuencia);
}
