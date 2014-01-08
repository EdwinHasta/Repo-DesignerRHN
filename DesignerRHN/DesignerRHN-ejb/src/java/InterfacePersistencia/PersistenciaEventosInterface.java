/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Eventos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Eventos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaEventosInterface {
    /**
     * Método encargado de insertar un Evento en la base de datos.
     * @param eventos Evento que se quiere crear.
     */
    public void crear(Eventos eventos);
    /**
     * Método encargado de modificar un Evento de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param eventos Evento con los cambios que se van a realizar.
     */
    public void editar(Eventos eventos);
    /**
     * Método encargado de eliminar de la base de datos el Evento que entra por parámetro.
     * @param eventos Evento que se quiere eliminar.
     */
    public void borrar(Eventos eventos);
    /**
     * Método encargado de buscar el Evento con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Evento que se quiere encontrar.
     * @return Retorna el Evento identificado con la secuencia dada por parámetro.
     */
    public Eventos buscarEvento(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Eventos existentes en la base de datos.
     * @return Retorna una lista de Eventos.
     */
    public List<Eventos> buscarEventos();
    public BigInteger contadorVigenciasEventos(BigInteger secuencia);
}
