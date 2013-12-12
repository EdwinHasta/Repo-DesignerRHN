package Persistencia;

import Entidades.VigenciasGruposConceptos;
import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasGruposConceptos implements PersistenciaVigenciasGruposConceptosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasGruposConceptos gruposConceptos) {
        try {
            em.persist(gruposConceptos);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(VigenciasGruposConceptos gruposConceptos) {
        try {
            em.merge(gruposConceptos);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(VigenciasGruposConceptos gruposConceptos) {
        try {
            em.remove(em.merge(gruposConceptos));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasGruposConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public boolean verificacionZonaTipoTrabajador(BigInteger secuenciaConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(vgc) FROM VigenciasGruposConceptos vgc WHERE vgc.concepto.secuencia = :secuenciaConcepto AND vgc.gruposConceptos.codigo = 1");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                return true;
            }
            return false;
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
