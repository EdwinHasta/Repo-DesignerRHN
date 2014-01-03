/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Conceptos;
import InterfacePersistencia.PersistenciaConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Conceptos' 
 * de la base de datos.
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaConceptos implements PersistenciaConceptosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Conceptos concepto) {
        em.persist(concepto);
    }

    @Override
    public void editar(Conceptos concepto) {
        em.merge(concepto);
    }

    @Override
    public void borrar(Conceptos concepto) {
        em.remove(em.merge(concepto));
    }

    @Override
    public List<Conceptos> buscarConceptos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Conceptos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public boolean verificarCodigoConcepto(BigInteger codigoConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Conceptos c WHERE c.codigo = :codigo");
            query.setParameter("codigo", codigoConcepto);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public Conceptos validarCodigoConcepto(BigInteger codigoConcepto, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.codigo = :codigo AND c.empresa.secuencia = :secEmpresa");
            query.setParameter("codigo", codigoConcepto);
            query.setParameter("secEmpresa", secEmpresa);
            Conceptos concepto = (Conceptos) query.getSingleResult();
            return concepto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Conceptos> conceptosPorEmpresa(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.empresa.secuencia = :secEmpresa ORDER BY c.codigo ASC");
            query.setParameter("secEmpresa", secEmpresa);
            List<Conceptos> listaConceptos = query.getResultList();
            return listaConceptos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Conceptos> conceptosEmpresaActivos_Inactivos(BigInteger secEmpresa, String estado) {
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.empresa.secuencia = :secEmpresa AND c.activo = :estado ORDER BY c.codigo ASC");
            query.setParameter("secEmpresa", secEmpresa);
            query.setParameter("estado", estado);
            List<Conceptos> listaConceptos = query.getResultList();
            return listaConceptos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Conceptos> conceptosEmpresaSinPasivos(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.empresa.secuencia = :secEmpresa AND c.naturaleza <> 'L' ORDER BY c.codigo ASC");
            query.setParameter("secEmpresa", secEmpresa);
            List<Conceptos> listaConceptos = query.getResultList();
            return listaConceptos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void clonarConcepto(BigInteger secConceptoOrigen, BigInteger codigoConceptoNuevo, String descripcionConceptoNuevo) {
        try {
            String sqlQuery = "call CONCEPTOS_PKG.CLONARCONCEPTO(?, ?, ?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secConceptoOrigen);
            query.setParameter(2, codigoConceptoNuevo);
            query.setParameter(3, descripcionConceptoNuevo);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en clonarConcepto: " + e);
        }
    }

    @Override
    public Conceptos conceptosPorSecuencia(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT c FROM Conceptos c WHERE c.secuencia=:secConcepto");
            query.setParameter("secConcepto", secConcepto);
            Conceptos conceptos = (Conceptos) query.getSingleResult();
            return conceptos;
        } catch (Exception e) {
            System.out.println("Error Persistencia conceptosPorSecuencia : " + e.toString());
            return null;
        }
    }

    @Override
    public boolean eliminarConcepto(BigInteger secuenciaConcepto) {
        try {
            String sqlQuery = "call conceptos_pkg.Eliminarconcepto(:secuencia)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter("secuencia", secuenciaConcepto);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error eliminarConcepto PersistenciaConceptos : "+e.toString());
            return false;
        }
    }

}
