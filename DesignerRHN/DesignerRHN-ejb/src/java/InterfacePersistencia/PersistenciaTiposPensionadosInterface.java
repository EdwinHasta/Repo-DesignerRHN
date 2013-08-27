/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposPensionados;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTiposPensionadosInterface {
    
    /**
     * Crea un objeto TiposPensionados
     * @param tiposPensionados Objeto a crear
     */
    public void crear(TiposPensionados tiposPensionados);
    /**
     * Editar un objeto TiposPensionados
     * @param tiposPensionados Objeto a editar
     */
    public void editar(TiposPensionados tiposPensionados);
    /**
     * Borra un objeto TiposPensionados
     * @param tiposPensionados Objeto a borrar
     */
    public void borrar(TiposPensionados tiposPensionados);
    /**
     * Metodo que obtiene un TiposPensionados por la llave primaria ID
     * @param id Llave Primaria ID
     * @return tipoP Tipo Pension que cumple con la llave primaria ID
     */
    public TiposPensionados buscarTipoPension(Object id);
    /**
     * Metodo que obtiene todos los TiposPensionados de la tabla
     * @return listTP Lista de Tipos Pensionados
     */
    public List<TiposPensionados> buscarTiposPensionados();
    /**
     * Metodo que obtiene un TipoPension por la secuencia
     * @param secuencia Secuencia del TipoPension
     * @return tP Tipo Pension que cumple con la secuencia dada
     */
    public TiposPensionados buscarTipoPensionSecuencia(BigDecimal secuencia); 
}
