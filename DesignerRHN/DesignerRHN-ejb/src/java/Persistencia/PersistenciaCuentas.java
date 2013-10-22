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
 *
 * @author user
 */
@Stateful
public class PersistenciaCuentas implements PersistenciaCuentasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(Cuentas cuentas) {
        try {
            em.persist(cuentas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(Cuentas cuentas) {
        try {
            em.merge(cuentas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(Cuentas cuentas) {
        try {
            em.remove(em.merge(cuentas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public Cuentas buscarCuenta(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Cuentas.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarCuenta PersistenciaCuentas : " + e.toString());
            return null;
        }

    }

    /*
     */
    @Override
    public List<Cuentas> buscarCuentas() {
        try {
            List<Cuentas> cuentas = (List<Cuentas>) em.createNamedQuery("Cuentas.findAll").getResultList();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentas PersistenciaCuentas : " + e.toString());
            return null;
        }
    }

    @Override
    public Cuentas buscarCuentasSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Cuentas c WHERE c.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Cuentas cuentas = (Cuentas) query.getSingleResult();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentasSecuencia PersistenciaCuentas : " + e.toString());
            Cuentas cuentas = null;
            return cuentas;
        }
    }

    public List<Cuentas> buscarCuentasSecuenciaEmpresa(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Cuentas c WHERE c.empresa.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            List<Cuentas> cuentas = (List<Cuentas>) query.getResultList();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentasSecuenciaEmpresa PersistenciaCuentas : " + e.toString());
            List<Cuentas> cuentas = null;
            return cuentas;
        }
    }
}
