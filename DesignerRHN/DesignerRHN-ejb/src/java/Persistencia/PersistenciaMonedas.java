/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Monedas;
import InterfacePersistencia.PersistenciaMonedasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Viktor
 */
@Stateless

public class PersistenciaMonedas implements PersistenciaMonedasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
     @Override
    public Monedas buscarMonedaSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT m.nombre FROM Monedas m WHERE m.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Monedas monedas = (Monedas) query.getSingleResult();
            return monedas;
        } catch (Exception e) {
            System.out.println("Error buscarMonedaSecuencia PersistenciaMonedas");
            Monedas monedas = null;
            return monedas;
        }

    }
     @Override
     public List<Monedas> listMonedas(){
         try{
             Query query = em.createQuery("SELECT m FROM Monedas m");
             List<Monedas> moendas = (List<Monedas>) query.getResultList();
            return moendas;
         }catch(Exception e){
             System.out.println("Error listMonedas PersistenciaMonedas : "+e.toString());
             return null;
         }
     }

}
