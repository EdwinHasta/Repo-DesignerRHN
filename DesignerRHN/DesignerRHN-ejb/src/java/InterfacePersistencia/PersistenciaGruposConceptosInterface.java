/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.GruposConceptos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaGruposConceptosInterface {
    
    /**
     * Crea un nuevo GruposConceptos
     * @param gruposConceptos Objeto a crear
     */
    public void crear(GruposConceptos gruposConceptos);
    /**
     * Edita un GruposConceptos
     * @param gruposConceptos Objeto a editar
     */
    public void editar(GruposConceptos gruposConceptos);
    /**
     * Borra un GruposConceptos
     * @param gruposConceptos Objeto a borrar
     */
    public void borrar(GruposConceptos gruposConceptos);
    /**
     * Obtiene un GruposConceptos por su llave primaria ID
     * @param id Llave Primaria ID
     * @return GruposConceptos que cumple con la ID
     */
    public GruposConceptos buscarGrupoConcepto(Object id);
    /**
     * Obtiene la lista total de GruposConceptos
     * @return Lista de GruposConceptos
     */
    public List<GruposConceptos> buscarGruposConceptos();
    /**
     * Obtiene un GruposConceptos por su secuencia
     * @param secuencia Secuencia GruposConceptos
     * @return GruposConceptos que cumple con la secuencia
     */
    public GruposConceptos buscarGruposConceptosSecuencia(BigInteger secuencia);
    
}
