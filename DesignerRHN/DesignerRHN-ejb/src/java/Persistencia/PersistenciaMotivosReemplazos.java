/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.MotivosReemplazos;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import java.math.BigInteger;
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
public class PersistenciaMotivosReemplazos implements PersistenciaMotivosReemplazosInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<MotivosReemplazos> motivosReemplazos() {
        try {
            Query query = em.createQuery("SELECT mR FROM MotivosReemplazos mR ORDER BY mR.codigo");
            List<MotivosReemplazos> motivosReemplazos = query.getResultList();
            return motivosReemplazos;
        } catch (Exception e) {
            return null;
        }
    }
    
    

}
