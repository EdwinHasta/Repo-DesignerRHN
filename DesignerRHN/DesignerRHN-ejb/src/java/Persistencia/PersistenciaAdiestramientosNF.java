/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.AdiestramientosNF;
import InterfacePersistencia.PersistenciaAdiestramientosNFInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaAdiestramientosNF implements PersistenciaAdiestramientosNFInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<AdiestramientosNF> adiestramientosNF() {
        try {
            Query query = em.createQuery("SELECT aNF FROM AdiestramientosNF aNF ORDER BY aNF.desccripcion");
            List<AdiestramientosNF> adiestramientosNF = query.getResultList();
            return adiestramientosNF;
        } catch (Exception e) {
            return null;
        }
    }

    
}
