package Persistencia;

import InterfacePersistencia.PersistenciaVigenciasConceptosTCInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosTC implements PersistenciaVigenciasConceptosTCInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public boolean verificacionZonaTipoContrato(BigInteger secuenciaConcepto, BigInteger secuenciaTC) {
        try {
            Query query = em.createQuery("SELECT COUNT(vcTC) FROM VigenciasConceptosTC vcTC WHERE vcTC.concepto.secuencia = :secuenciaConcepto AND vcTC.tipocontrato.secuencia = :secuenciaTC");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            query.setParameter("secuenciaTC", secuenciaTC);
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosTC: " + e);
            return false;
        }
    }
}
