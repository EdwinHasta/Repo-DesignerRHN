/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Proyectos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Proyectos' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaProyectosInterface {    
    /**
     * Método encargado de insertar un Proyecto en la base de datos.
     * @param proyectos Proyecto que se quiere crear.
     */
    public void crear(Proyectos proyectos);
    /**
     * Método encargado de modificar un Proyecto de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param proyectos Proyecto con los cambios que se van a realizar.
     */
    public void editar(Proyectos proyectos);
    /**
     * Método encargado de eliminar de la base de datos el Proyecto que entra por parámetro.
     * @param proyectos Proyecto que se quiere eliminar.
     */
    public void borrar(Proyectos proyectos);
    /**
     * Método encargado de buscar el Proyecto con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Proyecto que se quiere encontrar.
     * @return Retorna el Proyecto identificado con la secuencia dada por parámetro.
     */
    public Proyectos buscarProyectoSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Proyectos existentes en la base de datos, ordenados por nombre.
     * @return Retorna una lista de Proyectos ordenados por nombre.
     */
    public List<Proyectos> proyectos();
    /**
     * Método encargado de buscar un Proyecto por un nombre específico.
     * @param nombreP Nombre del proyecto del que se quiere la información.
     * @return Retorna el Proyecto cuyo nombre coincide con el parámetro.
     */
    public Proyectos buscarProyectoNombre(String nombreP);
}
