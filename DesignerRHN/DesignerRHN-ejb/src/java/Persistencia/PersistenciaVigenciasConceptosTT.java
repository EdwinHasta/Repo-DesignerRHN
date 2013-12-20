/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasConceptosTT;
import InterfacePersistencia.PersistenciaVigenciasConceptosTTInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasConceptosTT'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasConceptosTT implements PersistenciaVigenciasConceptosTTInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override 
    public void crear(VigenciasConceptosTT conceptosTT) {
        try {
            em.persist(conceptosTT);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Persistencia : " + e.toString());
        }
    }

    @Override 
    public void editar(VigenciasConceptosTT conceptosTT) {
        try {
            em.merge(conceptosTT);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTT Persistencia : " + e.toString());
        }
    }

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
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosTT: " + e);
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
            System.out.println("Exepcion listVigenciasConceptosTTPorConcepto PersistenciaVigenciasConceptosTT: " + e.toString());
            return null;
        }
    }
}
