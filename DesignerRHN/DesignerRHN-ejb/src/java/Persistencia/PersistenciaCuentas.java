/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Cuentas;
import InterfacePersistencia.PersistenciaCuentasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Cuentas'
 * de la base de datos
 * @author betelgeuse
 */
@Stateful
public class PersistenciaCuentas implements PersistenciaCuentasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
   /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,Cuentas cuentas) {
        try {
            em.persist(cuentas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCuentas : " + e.toString());
        }
    }
    
    @Override
    public void editar(EntityManager em,Cuentas cuentas) {
        try {
            em.merge(cuentas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCuentas : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em,Cuentas cuentas) {
        try {
            em.remove(em.merge(cuentas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCuentas : " + e.toString());
        }
    }

    @Override
    public List<Cuentas> buscarCuentas(EntityManager em) {
        try {
            List<Cuentas> cuentas = (List<Cuentas>) em.createNamedQuery("Cuentas.findAll").getResultList();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentas PersistenciaCuentas : " + e.toString());
            return null;
        }
    }

    @Override
    public Cuentas buscarCuentasSecuencia(EntityManager em,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Cuentas c WHERE c.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Cuentas cuentas = (Cuentas) query.getSingleResult();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentasSecuencia PersistenciaCuentas : " + e.toString());
            Cuentas cuentas = null;
            return cuentas;
        }
    }

    @Override
    public List<Cuentas> buscarCuentasSecuenciaEmpresa(EntityManager em,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Cuentas c WHERE c.empresa.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Cuentas> cuentas = (List<Cuentas>) query.getResultList();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentasSecuenciaEmpresa PersistenciaCuentas : " + e.toString());
            List<Cuentas> cuentas = null;
            return cuentas;
        }
    }
}
