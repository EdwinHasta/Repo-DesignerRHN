/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Query;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'HvEntrevistas' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaHvEntrevistasInterface {

    public void crear(HvEntrevistas hvEntrevistas);

    public void editar(HvEntrevistas hvEntrevistas);

    public void borrar(HvEntrevistas hvEntrevistas);

    public HvEntrevistas buscarHvEntrevista(BigInteger secuenciaHvEntrevista);

    public List<HvEntrevistas> buscarHvEntrevistas();

    public List<HvEntrevistas> buscarHvEntrevistasPorEmpleado(BigInteger secEmpleado);

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado);

    /**
     * Método encargado de recuperar las ultimas HvEntrevistas realizadas para
     * una hoja de vida.
     *
     * @param secuenciaHV Secuencia de la hoja de vida.
     * @return Retorna una lista de HvEntrevistas asociadas a una hoja de vida y
     * realizadas el mismo día.
     */
    public List<HvEntrevistas> entrevistasPersona(BigInteger secuenciaHV);
}
