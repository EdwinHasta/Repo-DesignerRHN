/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosEmbargosInterface;
import Entidades.MotivosEmbargos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosEmbargos'
 * de la base de datos.
 * @author John Pineda
 */
@Stateless
public class PersistenciaMotivosEmbargos implements PersistenciaMotivosEmbargosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
     public void crear(MotivosEmbargos motivosEmbargos) {
        em.persist(motivosEmbargos);
    }

    @Override
    public void editar(MotivosEmbargos motivosEmbargos) {
        em.merge(motivosEmbargos);
    }

    @Override
    public void borrar(MotivosEmbargos motivosEmbargos) {
        try {
            em.remove(em.merge(motivosEmbargos));
        } catch (Exception e) {
            System.err.println("Error borrando MotivosEmbargos");
            System.out.println(e);
        }
    }

    @Override
    public MotivosEmbargos buscarMotivoEmbargo(BigInteger secuenciaME) {
        try {
            return em.find(MotivosEmbargos.class, secuenciaME);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MotivosEmbargos> buscarMotivosEmbargos() {
        Query query = em.createQuery("SELECT m FROM MotivosEmbargos m ORDER BY m.codigo ASC");
        List<MotivosEmbargos> listaMotivosEmbargos = query.getResultList();
        return listaMotivosEmbargos;
    }

    @Override
    public BigInteger contadorEersPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos eer WHERE eer.motivoembargo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSEMBARGOS CONTADOREERSPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSEMBARGOS CONTADOREERSPRESTAMOS  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorEmbargos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM  embargos emb WHERE emb.motivo = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSEMBARGOS CONTADOREMBARGOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSEMBARGOS CONTADOREMBARGOS  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
