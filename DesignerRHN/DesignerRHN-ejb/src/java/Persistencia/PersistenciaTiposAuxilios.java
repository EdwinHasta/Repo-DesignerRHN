/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposAuxiliosInterface;
import Entidades.TiposAuxilios;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposAuxilios' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposAuxilios implements PersistenciaTiposAuxiliosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, TiposAuxilios tiposAuxilios) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposAuxilios);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposAuxilios.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TiposAuxilios tiposAuxilios) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposAuxilios);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposAuxilios.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TiposAuxilios tiposAuxilios) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(tiposAuxilios));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposAuxilios.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public TiposAuxilios buscarTipoAuxilio(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(TiposAuxilios.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposAuxilios> buscarTiposAuxilios(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM TiposAuxilios m ORDER BY m.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<TiposAuxilios> listaMotivosEmbargos = query.getResultList();
        return listaMotivosEmbargos;
    }

    @Override
    public BigInteger contadorTablasAuxilios(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM tablasauxilios ta WHERE ta.tipoauxilio = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSAUXILIOS contadorTablasAuxilios = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSAUXILIOS contadorTablasAuxilios  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
