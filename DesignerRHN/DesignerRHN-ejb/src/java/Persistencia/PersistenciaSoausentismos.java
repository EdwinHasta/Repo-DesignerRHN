/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Soausentismos;
import InterfacePersistencia.PersistenciaSoausentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaSoausentismos implements PersistenciaSoausentismosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Soausentismos soausentismos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(soausentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaSoausentismos.crear");
        }
    }

    // Editar Ausentismos. 
    @Override
    public void editar(Soausentismos soausentismos) {
        em.merge(soausentismos);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Soausentismos soausentismos) {
        em.remove(em.merge(soausentismos));
    }

//Trae los Ausentismos de un Empleado
    public List<Soausentismos> ausentismosEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soausentismos soa WHERE soa.empleado.secuencia= :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Soausentismos> todosAusentismos = query.getResultList();
            return todosAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
    //Lista de Valores de Prorrogas
    @Override
    public List<Soausentismos> prorrogas(BigInteger secuenciaEmpleado, BigInteger secuenciaCausa, BigInteger secuenciaAusentismo) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soausentismos soa WHERE soa.empleado.secuencia= :secuenciaEmpleado AND soa.causa.secuencia= :secuenciaCausa AND soa.secuencia= :secuenciaAusentismo");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setParameter("secuenciaCausa", secuenciaCausa);
            query.setParameter("secuenciaAusentismo", secuenciaAusentismo);
            List<Soausentismos> prorrogas = query.getResultList();
            return prorrogas;
        } catch (Exception e) {
            System.out.println("Error: (prorrogas)" + e);
            return null;
        }
    }
    
    //Prorroga que se mostrará en la tabla.
    public String prorrogaMostrar(BigInteger secuenciaProrroga) {
        try {
            String sqlQuery = ("SELECT nvl(A.NUMEROCERTIFICADO,'Falta # Certificado')||':'||A.fecha||'->'||A.fechafinaus\n"
                    + "FROM SOAUSENTISMOS A\n"
                    + "WHERE A.SECUENCIA = ?");
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuenciaProrroga);
            String resultado = (String) query.getSingleResult();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error: (prorrogaMostrar)" + e);
            return null;
        }
    }
    
    
    
}
