/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Clasesausentismos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'ClasesAusentismos' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaClasesAusentismosInterface {

    /**
     * Método encargado de insertar una ClaseAusentismo en la base de datos.
     *
     * @param clasesAusentismos ClaseAusentismo que se quiere crear.
     */
    public void crear(Clasesausentismos clasesAusentismos);

    /**
     * Método encargado de modificar una ClaseAusentismo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param clasesAusentismos ClaseAusentismo con los cambios que se van a
     * realizar.
     */
    public void editar(Clasesausentismos clasesAusentismos);

    /**
     * Método encargado de eliminar de la base de datos la ClaseAusentismo que
     * entra por parámetro.
     *
     * @param clasesAusentismos ClaseAusentismo que se quiere eliminar.
     */
    public void borrar(Clasesausentismos clasesAusentismos);

    /**
     * Método encargado de buscar todas las ClasesAusentismos existentes en la
     * base de datos.
     *
     * @return Retorna una lista de ClasesAusentismos.
     */
    public List<Clasesausentismos> buscarClasesAusentismos();

    public BigInteger contadorCausasAusentismosClaseAusentismo(BigInteger secuencia);

    public BigInteger contadorSoAusentismosClaseAusentismo(BigInteger secuencia);

}
