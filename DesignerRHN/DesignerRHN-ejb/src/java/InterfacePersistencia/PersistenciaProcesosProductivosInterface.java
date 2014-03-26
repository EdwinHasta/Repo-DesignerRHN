/**
 * Documentación a cargo de AndresPineda
 */
package InterfacePersistencia;

import Entidades.ProcesosProductivos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'ProcesosProductivos' de la base de datos.
 *
 * @author AndresPineda
 */
public interface PersistenciaProcesosProductivosInterface {

    /**
     * Método encargado de insertar un ProcesoProductivo en la base de datos.
     *
     * @param procesos ProcesoProductivo que se quiere crear.
     */
    public void crear(ProcesosProductivos procesos);

    /**
     * Método encargado de modificar un ProcesoProductivo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param procesos ProcesoProductivo con los cambios que se van a realizar.
     */
    public void editar(ProcesosProductivos procesos);

    /**
     * Método encargado de eliminar de la base de datos el ProcesoProductivo que
     * entra por parámetro.
     *
     * @param procesos ProcesoProductivo que se quiere eliminar.
     */
    public void borrar(ProcesosProductivos procesos);

    /**
     * Método encargado de buscar todos los ProcesosProductivos existentes en la
     * base de datos.
     *
     * @return Retorna una lista de ProcesosProductivos.
     */
    public List<ProcesosProductivos> consultarProcesosProductivos();

    /**
     * Método encargado de buscar el ProcesoProductivo con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia del ProcesoProductivo que se quiere encontrar.
     * @return Retorna el ProcesoProductivo identificado con la secuencia dada
     * por parámetro.
     */
    public ProcesosProductivos consultarProcesosProductivos(BigInteger secuencia);

    public BigInteger contarUnidadesProducidasProcesoProductivo(BigInteger secuencia);

    public BigInteger contarTarifasProductosProcesoProductivo(BigInteger secuencia);

    public BigInteger contarCargosProcesoProductivo(BigInteger secuencia);
}
