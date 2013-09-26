/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.AdiestramientosF;
import InterfacePersistencia.PersistenciaAdiestramientosFInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class PersistenciaAdiestramientosF implements PersistenciaAdiestramientosFInterface {

 @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<AdiestramientosF> adiestramientosF() {
        try {
            Query query = em.createQuery("SELECT aF FROM AdiestramientosF aF ORDER BY aF.descripcion");
            List<AdiestramientosF> adiestramientosF = query.getResultList();
            return adiestramientosF;
        } catch (Exception e) {
            return null;
        }
    }
}
    

