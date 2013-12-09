package Persistencia;

import Entidades.VigenciasConceptosRL;
import InterfacePersistencia.PersistenciaVigenciasConceptosRLInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosRL implements PersistenciaVigenciasConceptosRLInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(VigenciasConceptosRL conceptosRL) {
        try {
            em.persist(conceptosRL);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRL Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(VigenciasConceptosRL conceptosRL) {
        try {
            em.merge(conceptosRL);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRL Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(VigenciasConceptosRL conceptosRL) {
        try {
            em.remove(em.merge(conceptosRL));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRLa Persistencia : " + e.toString());
        }
    }

    
    @Override
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
    
    @Override
    public List<VigenciasConceptosRL> listVigenciasConceptosRLPorConcepto(BigInteger secuenciaC){
        try {
            Query query = em.createQuery("SELECT vcRL FROM VigenciasConceptosRL vcRL WHERE vcRL.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuenciaC);
            List<VigenciasConceptosRL> resultado = (List<VigenciasConceptosRL>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosRL: " + e);
            return null;
        }
    }
}
