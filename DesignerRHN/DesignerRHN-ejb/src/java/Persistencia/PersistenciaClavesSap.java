/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.ClavesSap;
import InterfacePersistencia.PersistenciaClavesSapInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaClavesSap implements PersistenciaClavesSapInterface {

    public List<ClavesSap> consultarClavesSap(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT cp FROM ClavesSap cp");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ClavesSap> claseP = query.getResultList();
            return claseP;
        } catch (Exception e) {
            System.out.println("Error buscarClasePennsion PersistenciaClavesSap");
            List<ClavesSap> claseP = null;
            return claseP;
        }
    }
}
