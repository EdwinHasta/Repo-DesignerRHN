package Persistencia;

import Entidades.VigenciasConceptosTT;
import InterfacePersistencia.PersistenciaVigenciasConceptosTTInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasConceptosTT implements PersistenciaVigenciasConceptosTTInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    
    /*
     */
    @Override 
    public void crear(VigenciasConceptosTT conceptosTT) {
        try {
            em.persist(conceptosTT);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Persistencia : " + e.toString());
        }
    }

    /*
     */
    @Override 
    public void editar(VigenciasConceptosTT conceptosTT) {
        try {
            em.merge(conceptosTT);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Persistencia : " + e.toString());
        }
    }

    /*
     */ 
    @Override 
    public void borrar(VigenciasConceptosTT conceptosTT) {
        try {
            em.remove(em.merge(conceptosTT));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Persistencia : " + e.toString());
        }
    }

    @Override
    public boolean verificacionZonaTipoTrabajador(BigInteger secuenciaConcepto, BigInteger secuenciaTT) {
        try {
            Query query = em.createQuery("SELECT vcTT FROM VigenciasConceptosTT vcTT WHERE vcTT.concepto.secuencia = :secuenciaConcepto AND vcTT.tipotrabajador.secuencia = :secuenciaTT");
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
    
    @Override
    public List<VigenciasConceptosTT> listVigenciasConceptosTTPorConcepto(BigInteger secuenciaC){
        try {
            Query query = em.createQuery("SELECT vcTT FROM VigenciasConceptosTT vcTT WHERE vcTT.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuenciaC);
            List<VigenciasConceptosTT> resultado = (List<VigenciasConceptosTT>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion listVigenciasConceptosTTPorConcepto PersistenciaVigenciasConceptosRL: " + e.toString());
            return null;
        }
    }
}
