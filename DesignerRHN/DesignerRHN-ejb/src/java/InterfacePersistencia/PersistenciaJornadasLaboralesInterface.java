package InterfacePersistencia;

import Entidades.JornadasLaborales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PersistenciaJornadasLaboralesInterface {
    
    /**
     * Crea una nueva JornadaLaboral
     * @param jornadasLaborales Objeto a crear
     */
    public void crear(JornadasLaborales jornadasLaborales);
    /**
     * Edita una JornadaLaboral
     * @param jornadasLaborales Objeto a editar
     */
    public void editar(JornadasLaborales jornadasLaborales);
    /**
     * Borra una JornadaLaboral
     * @param jornadasLaborales Objeto a borrar
     */
    public void borrar(JornadasLaborales jornadasLaborales);
    /**
     * Busca una JornadaLaboral por la llave primaria ID
     * @param id Llave Primaria ID
     * @return JornadaLaboral que cumpe con el ID
     */
    public JornadasLaborales buscarJornadaLaboral(Object id);
    /**
     * Obtiene la lista de JornadaLaboral
     * @return Lista de JornadaLaboral
     */
    public List<JornadasLaborales> buscarJornadasLaborales();
    /**
     * Obtiene una JornadaLaboral por la secuencia
     * @param secuencia Secuencia JornadaLaboral
     * @return JornadaLaboral que cumple con la secuencia
     */
    public JornadasLaborales buscarJornadaLaboralSecuencia(BigInteger secuencia);
    
}
