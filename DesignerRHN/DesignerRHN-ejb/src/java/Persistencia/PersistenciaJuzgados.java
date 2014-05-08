/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaJuzgadosInterface;
import Entidades.Juzgados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Juzgados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaJuzgados implements PersistenciaJuzgadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
//     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Juzgados juzgados) {
        em.persist(juzgados);
    }

    @Override
    public void editar(EntityManager em, Juzgados juzgados) {
        em.merge(juzgados);
    }

    @Override
    public void borrar(EntityManager em, Juzgados juzgados) {
        try {
            em.remove(em.merge(juzgados));
        } catch (Exception e) {
            System.err.println("PERSISTENCIA JUZGADOS ERROR : ");
            System.out.println(e);
        }
    }

    @Override
    public Juzgados buscarJuzgado(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(Juzgados.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Juzgados> buscarJuzgados(EntityManager em) {
        Query query = em.createQuery("SELECT m FROM Juzgados m ORDER BY m.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Juzgados> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    @Override
    public List<Juzgados> buscarJuzgadosPorCiudad(EntityManager em, BigInteger secCiudad) {
        try {
            Query query = em.createQuery("SELECT cce FROM Juzgados cce WHERE cce.ciudad.secuencia = :secuenciaJuzgado ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaJuzgado", secCiudad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Juzgados> listaJuzgadosPorCiudad = query.getResultList();
            return listaJuzgadosPorCiudad;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            return null;
        }
    }

    @Override
    public BigInteger contadorEerPrestamos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia);
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos ers , juzgados juz WHERE ers.juzgado = juz.secuencia AND juz.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADOREERPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAJUZGADOS CONTADOREERPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

}
