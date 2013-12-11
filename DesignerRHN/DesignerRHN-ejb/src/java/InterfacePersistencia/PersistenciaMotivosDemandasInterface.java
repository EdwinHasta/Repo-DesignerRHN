/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MotivosDemandas;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosDemandas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaMotivosDemandasInterface {    
    /**
     * Método encargado de insertar un MotivoDemanda en la base de datos.
     * @param motivosDemandas MotivoDemanda que se quiere crear.
     */
    public void crear(MotivosDemandas motivosDemandas);
    /**
     * Método encargado de modificar un MotivoDemanda de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param motivosDemandas MotivoDemanda con los cambios que se van a realizar.
     */
    public void editar(MotivosDemandas motivosDemandas);
    /**
     * Método encargado de eliminar de la base de datos el MotivoDemanda que entra por parámetro.
     * @param motivosDemandas MotivoDemanda que se quiere eliminar.
     */
    public void borrar(MotivosDemandas motivosDemandas);
    /**
     * Método encargado de buscar todos los MotivosDemandas existentes en la base de datos, ordenados por secuencia.
     * @return Retorna una lista de MotivosDemandas ordenados por secuencia.
     */
    public List<MotivosDemandas> buscarMotivosDemandas();
}
