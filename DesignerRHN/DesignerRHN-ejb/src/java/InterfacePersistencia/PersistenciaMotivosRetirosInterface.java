/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.MotivosRetiros;
import java.math.BigDecimal;
import java.util.List;

/**
 * Persistencia Motivos Retiros
 * @author AndresPineda
 */
public interface PersistenciaMotivosRetirosInterface {
    
    /**
     * Crea un objeto de tipo Motivos Retiros
     * @param motivosRetiros Objeto a crear
     */
    public void crear(MotivosRetiros motivosRetiros);
    /**
     * Editar un objeto de tipo Motivos Retiros
     * @param motivosRetiros Objeto a editar
     */
    public void editar(MotivosRetiros motivosRetiros);
    /**
     * Borra un objeto de tipo Motivo Retiros
     * @param motivosRetiros Objeto a borrar
     */
    public void borrar(MotivosRetiros motivosRetiros);
    /**
     * Metodo que buscar un Motivo Retiro por su ID
     * @param id Llave Primaria ID
     * @return motivoRetiro Motivo Retiro que cumple con la condicion de la llave primaria
     */
    public MotivosRetiros buscarMotivoRetiro(Object id);
    /**
     * Metodo que busca todo los elementos de la tabala Motivos Retiros
     * @return listaMR Lista que contiene todos los elementos de la tabla Motivos Retiros
     */
    public List<MotivosRetiros> buscarMotivosRetiros();
    /**
     * Metodo que busca un Motivo Retiro por su secuencia
     * @param secuencia Secuencia a buscar 
     * @return motivoRetiro Motivo retiro que cumple con la condicion de la secuencia
     */
    public MotivosRetiros buscarMotivoRetiroSecuencia(BigDecimal secuencia);
}
