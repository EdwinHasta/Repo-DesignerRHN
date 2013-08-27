package Persistencia;

import InterfacePersistencia.PersistenciaVigenciasConceptosRLInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosRL implements PersistenciaVigenciasConceptosRLInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public boolean verificacionZonaTipoReformasLaborales(BigInteger secuenciaConcepto, BigInteger secuenciaTS) {
        try {
            Query query = em.createQuery("SELECT COUNT(vcRL) FROM VigenciasConceptosRL vcRL WHERE vcRL.concepto.secuencia = :secuenciaConcepto AND vcRL.tiposalario.secuencia = :secuenciaTS");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            query.setParameter("secuenciaTS", secuenciaTS);
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
