/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HVHojasDeVida;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
//import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'HVHojasDeVida' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHVHojasDeVida implements PersistenciaHVHojasDeVidaInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     *
     * @param em
     * @param hVHojasDeVida
     */
    @Override
    public void editar(EntityManager em, HVHojasDeVida hVHojasDeVida) {
        System.out.println(this.getClass().getName() + ".hvHoaDeVidaPersona()");
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(hVHojasDeVida);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("error en editar");
            e.printStackTrace();
        }
    }

    @Override
    public HVHojasDeVida hvHojaDeVidaPersona(EntityManager em, BigInteger secuenciaPersona) {
        System.out.println(this.getClass().getName() + ".hvHojaDeVidaPersona()");
        try {
            em.clear();
            Query query = em.createQuery("SELECT hv from HVHojasDeVida hv where hv.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            HVHojasDeVida hVHojasDeVida = (HVHojasDeVida) query.getSingleResult();
            return hVHojasDeVida;
        } catch (Exception e) {
            System.out.println("error en hvHojaDeVidaPersona");
            e.printStackTrace();
            return null;
        }
    }
}
