/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaPartesCuerpoInterface;
import Entidades.PartesCuerpo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'PartesCuerpo'
 * de la base de datos
 * @author John Pineda.
 */
@Stateless
public class PersistenciaPartesCuerpo implements PersistenciaPartesCuerpoInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(PartesCuerpo partesCuerpo) {
        em.persist(partesCuerpo);
    }

    @Override
    public void editar(PartesCuerpo partesCuerpo) {
        em.merge(partesCuerpo);
    }

    @Override
    public void borrar(PartesCuerpo partesCuerpo) {
        em.remove(em.merge(partesCuerpo));
    }

    @Override
    public PartesCuerpo buscarParteCuerpo(BigInteger secuencia) {
        try {
            return em.find(PartesCuerpo.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PartesCuerpo> buscarPartesCuerpo() {
        try {
            Query query = em.createQuery("SELECT l FROM PartesCuerpo  l ORDER BY l.codigo ASC ");
            List<PartesCuerpo> listPartesCuerpo = query.getResultList();
            return listPartesCuerpo;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR ELEMENTOS CAUSAS ACCIDENTES  " + e);
            return null;
        }
    }

    @Override
    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM soaccidentesmedicos so WHERE so.parte = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADORSOACCIDENTESMEDICOS  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL CONTADORSOACCIDENTESMEDICOS " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorDetallesExamenes(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM sodetallesexamenes se WHERE se.partecuerpo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADOR DETALLES EXAMENES  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL DETALLES EXAMENES " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contadorSoDetallesRevisiones(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM sodetallesrevisiones sr WHERE sr.organo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("PARTESCUERPO CONTADOR SO DETALLES REVISIONES  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("PARTESCUERPO  ERROR EN EL CONTADORSODETALLESREVISIONES " + e);
            return retorno;
        }
    }
}
