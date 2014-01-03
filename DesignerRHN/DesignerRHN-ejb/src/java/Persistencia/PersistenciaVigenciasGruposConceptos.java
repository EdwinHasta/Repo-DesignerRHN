/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasGruposConceptos;
import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasGruposConceptos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasGruposConceptos implements PersistenciaVigenciasGruposConceptosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasGruposConceptos vigenciasGruposConceptos) {
        try {
            em.persist(vigenciasGruposConceptos);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasGruposConceptos vigenciasGruposConceptos) {
        try {
            em.merge(vigenciasGruposConceptos);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasGruposConceptos vigenciasGruposConceptos) {
        try {
            em.remove(em.merge(vigenciasGruposConceptos));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public boolean verificacionGrupoUnoConcepto(BigInteger secuenciaConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(vgc) FROM VigenciasGruposConceptos vgc WHERE vgc.concepto.secuencia = :secuenciaConcepto AND vgc.gruposConceptos.codigo = 1");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosRL: " + e);
            return false;
        }
    }

    @Override
    public List<VigenciasGruposConceptos> listVigenciasGruposConceptosPorConcepto(BigInteger secuenciaC) {
        try {
            Query query = em.createQuery("SELECT vgc FROM VigenciasGruposConceptos vgc WHERE vgc.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuenciaC);
            List<VigenciasGruposConceptos> resultado = (List<VigenciasGruposConceptos>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion listVigenciasGruposConceptosPorConcepto PersistenciaVigenciasGruposConceptos: " + e.toString());
            return null;
        }
    }
}
