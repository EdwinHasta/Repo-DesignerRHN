package Persistencia;

import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasGruposConceptos implements PersistenciaVigenciasGruposConceptosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

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
}
