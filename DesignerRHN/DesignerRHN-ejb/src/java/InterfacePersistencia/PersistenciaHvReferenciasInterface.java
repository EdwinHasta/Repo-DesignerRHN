/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'HvReferencias' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaHvReferenciasInterface {

    /**
     * Metodo encargado de buscar las referencias personales de una persona.
     *
     * @param secuenciaHV Secuencia de la hoja de vida de la persona.
     * @return Retorna una lista de HvReferencias con las referencias personales
     * de una persona.
     *
     */
    public List<HvReferencias> referenciasPersonalesPersona(BigInteger secuenciaHV);

    /**
     * Metodo encargado de buscar las referencias familiares de una persona.
     *
     * @param secuenciaHV Secuencia de la hoja de vida de la persona.
     * @return Retorna una lista de HvReferencias con las referencias familiares
     * de una persona.
     */
    public List<HvReferencias> referenciasFamiliaresPersona(BigInteger secuenciaHV);

    public void crear(HvReferencias hvReferencias);

    public void editar(HvReferencias hvReferencias);

    public void borrar(HvReferencias hvReferencias);

    public HvReferencias buscarHvReferencia(BigInteger secuenciaHvReferencias);

    public List<HvReferencias> buscarHvReferencias();

    public List<HvReferencias> buscarHvReferenciasPorEmpleado(BigInteger secEmpleado);

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado);
}
