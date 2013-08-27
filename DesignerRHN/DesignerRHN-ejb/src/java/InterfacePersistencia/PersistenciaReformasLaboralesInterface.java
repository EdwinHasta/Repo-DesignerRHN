/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ReformasLaborales;
import java.math.BigInteger;
import java.util.List;

/**
 * Persistencia Reformas Laborales
 * @author AndresPineda
 */
public interface PersistenciaReformasLaboralesInterface {
    
    /**
     * Crea un objeto ReformaLaboral
     * @param reformaLaboral Objeto a crear
     */
    public void crear(ReformasLaborales reformaLaboral);
    /**
     * Editar un objeto ReformaLaboral
     * @param reformaLaboral Objeto a editar
     */
    public void editar(ReformasLaborales reformaLaboral);
    /**
     * Borrar un objeto ReformaLaboral
     * @param reformaLaboral Objeto a borrar
     */
    public void borrar(ReformasLaborales reformaLaboral);
    /**
     * Metodo que busca una Reforma Laboral por la llave primaria ID
     * @param id Llave primaria ID
     * @return reformaLaboral Reforma Laboral que cumple con la llave primaria dada
     */
    public ReformasLaborales buscarReformaLaboral(Object id);
    /**
     * Metodo que busca todas las Reformas Laborales de la tabla
     * @return listRF Lista Reformas Laborales
     */
    public List<ReformasLaborales> buscarReformasLaborales();
    /**
     * Metodo que obtiene una Reforma Laboral por la secuencia
     * @param secuencia Secuencia de la Reforma Laboral 
     * @return reformaL Reforma Laboral que cumple con la secuencia dada
     */
    public ReformasLaborales buscarReformaSecuencia(BigInteger secuencia);
    
    
    
}
