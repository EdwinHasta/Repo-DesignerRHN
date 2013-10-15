/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposReemplazos;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author user
 */
@Stateless

public class PersistenciaTiposReemaplazos implements PersistenciaTiposReemplazosInterface {
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
     
    @Override
        public List<TiposReemplazos> tiposReemplazos() {
        try {
            Query query = em.createQuery("SELECT tR FROM TiposReemplazos tR ORDER BY tr.codigo");
            List<TiposReemplazos> tiposReemplazos = query.getResultList();
            return tiposReemplazos;
        } catch (Exception e) {
            return null;
        }
    }

    

}
