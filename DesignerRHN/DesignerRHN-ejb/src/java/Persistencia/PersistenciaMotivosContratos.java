package Persistencia;

import Entidades.MotivosContratos;
import InterfacePersistencia.PersistenciaMotivosContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

@Stateless
public class PersistenciaMotivosContratos implements PersistenciaMotivosContratosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosContratos motivosContratos) {
        try {
            em.persist(motivosContratos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos crear ERROR " + e);
        }
    }

    @Override
    public void editar(MotivosContratos motivosContratos) {
        try {
            em.merge(motivosContratos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos editar ERROR " + e);
        }
    }

    @Override
    public void borrar(MotivosContratos motivosContratos) {
        try {
            em.remove(em.merge(motivosContratos));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos borrar ERROR " + e);
        }
    }

    public MotivosContratos buscarMotivoContrato(BigInteger secuenciaMotivosContratos) {
        try {
            return em.find(MotivosContratos.class, secuenciaMotivosContratos);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaMotivosContratos buscarMotivosContratos ERROR " + e);
            return null;
        }
    }

    public List<MotivosContratos> motivosContratos() {
        try {
            Query query = em.createQuery("SELECT m FROM MotivosContratos m ORDER BY m.codigo");
            List<MotivosContratos> motivosContratos = query.getResultList();
            return motivosContratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MotivosContratos> buscarMotivosContratos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MotivosContratos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaMotivosContratos buscarMotivosCambiosCargos ERROR" + e);
            return null;
        }
    }

    @Override
    public Long verificarBorradoVigenciasTiposContratos(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(v) FROM VigenciasTiposContratos v WHERE v.motivocontrato.secuencia =:secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaMotivosContratos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaMotivosMotivosContratos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}
