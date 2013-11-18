/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Recordatorios;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrator
 */
public interface PersistenciaRecordatoriosInterface {
    public Recordatorios recordatorioRandom(EntityManager entity);
    public List<String> recordatoriosInicio(EntityManager entity);
    public List<Recordatorios> consultasInicio(EntityManager entity);
}
