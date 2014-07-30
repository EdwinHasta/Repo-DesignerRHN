/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PryClientes;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'PryClientes' de la
 * base de datos.
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaPryClientes implements PersistenciaPryClientesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    public void crear(EntityManager em, PryClientes pryClientes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(pryClientes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPryClientes.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void editar(EntityManager em, PryClientes pryClientes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(pryClientes);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPryClientes.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public void borrar(EntityManager em, PryClientes pryClientes) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(pryClientes));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaPryClientes.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public PryClientes buscarPryCliente(EntityManager em, BigInteger secuenciaPC) {
        try {
            em.clear();
            return em.find(PryClientes.class, secuenciaPC);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PryClientes> buscarPryClientes(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT pc FROM PryClientes pc ORDER BY pc.nombre ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PryClientes> pryclientes = query.getResultList();
            return pryclientes;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contadorProyectos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            em.clear();
            String sqlQuery = " SELECT COUNT(*)FROM  proyectos WHERE pry_cliente = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("persistenciapryclientes contadorProyectos Contador " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error  persistenciapryclientes contadorProyectos. " + e);
            return retorno;
        }
    }
}
