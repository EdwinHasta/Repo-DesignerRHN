package Persistencia;

import Entidades.VigenciasConceptosTC;
import InterfacePersistencia.PersistenciaVigenciasConceptosTCInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosTC implements PersistenciaVigenciasConceptosTCInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(VigenciasConceptosTC conceptosTC) {
        try {
            em.persist(conceptosTC);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(VigenciasConceptosTC conceptosTC) {
        try {
            em.merge(conceptosTC);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(VigenciasConceptosTC conceptosTC) {
        try {
            em.remove(em.merge(conceptosTC));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    @Override
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

    @Override
    public List<VigenciasConceptosTC> listVigenciasConceptosTCPorConcepto(BigInteger secuenciaC) {
        try {
            Query query = em.createQuery("SELECT vcTC FROM VigenciasConceptosTC vcTC WHERE vcTC.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuenciaC);
            List<VigenciasConceptosTC> resultado = (List<VigenciasConceptosTC>) query.getSingleResult();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion listVigenciasConceptosTCPorConcepto PersistenciaVigenciasConceptosTC : " + e.toString());
            return null;
        }
    }
}
