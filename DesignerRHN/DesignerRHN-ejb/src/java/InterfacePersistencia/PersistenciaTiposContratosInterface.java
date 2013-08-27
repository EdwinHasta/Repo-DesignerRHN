/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposContratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaTiposContratosInterface {

    /**
     * Crea un objeto TiposContratos
     *
     * @param tiposContratos Objeto a crear
     */
    public void crear(TiposContratos tiposContratos);

    /**
     * Editar un objeto TiposContratos
     *
     * @param tiposContratos Objeto a editar
     */
    public void editar(TiposContratos tiposContratos);

    /**
     * Borra un objeto TiposContratos
     *
     * @param tiposContratos Objeto a borrar
     */
    public void borrar(TiposContratos tiposContratos);

    /**
     * Metodo que obtiene un TipoContrato por la llave primaria ID
     *
     * @param id Llave Primaria ID
     * @return tipoContrato Tipo Contrato que cumple con la condicion de la
     * llave primaria
     */
    public TiposContratos buscarTipoContrato(Object id);

    /**
     * Metodo que obtiene todos los Tipos Contratos
     *
     * @return listTC Lista de Tipos Contratos
     */
    public List<TiposContratos> buscarTiposContratos();

    /**
     * Metodo que obtiene un TipoContrato por la secuencia
     *
     * @param secuencia Secuencia del TipoContrato
     * @return tipoC Tipo Contrato que cumple con la condicion de la secuencia
     */
    public TiposContratos buscarTipoContratoSecuencia(BigInteger secuencia);

    public List<TiposContratos> tiposContratos();
}
