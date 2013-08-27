package Persistencia;

import InterfacePersistencia.PersistenciaVigenciasConceptosTTInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosTT implements PersistenciaVigenciasConceptosTTInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public boolean verificacionZonaTipoTrabajador(BigInteger secuenciaConcepto, BigInteger secuenciaTT) {
        try {
            Query query = em.createQuery("SELECT COUNT(vcTT) FROM VigenciasConceptosTT vcTT WHERE vcTT.concepto.secuencia = :secuenciaConcepto AND vcTT.tipotrabajador.secuencia = :secuenciaTT");
            query.setParameter("secuenciaConcepto", secuenciaConcepto);
            query.setParameter("secuenciaTT", secuenciaTT);
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
