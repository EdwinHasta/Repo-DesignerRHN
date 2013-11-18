/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.OtrosCertificados;
import InterfacePersistencia.PersistenciaOtrosCertificadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaOtrosCertificados implements PersistenciaOtrosCertificadosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(OtrosCertificados certificados) {
        em.persist(certificados);
    }

    /*
     */
    @Override
    public void editar(OtrosCertificados certificados) {
        em.merge(certificados);
    }

    /*
     */
    @Override
    public void borrar(OtrosCertificados certificados) {
        em.remove(em.merge(certificados));
    }

    /*
     */
    @Override
    public OtrosCertificados buscarOtroCertificado(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(OtrosCertificados.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     */
    @Override
    public List<OtrosCertificados> buscarOtrosCertificados() {
        List<OtrosCertificados> certificados = (List<OtrosCertificados>) em.createNamedQuery("OtrosCertificados.findAll").getResultList();
        return certificados;
    }

    @Override
    public OtrosCertificados buscarOtroCertificadoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT oc FROM OtrosCertificados oc WHERE oc.secuencia= :secuencia");
            query.setParameter("secuencia", secuencia);
            OtrosCertificados certificados = (OtrosCertificados) query.getResultList();
            return certificados;
        } catch (Exception e) {
            OtrosCertificados certificados = null;
            return certificados;
        }
    }

    @Override
    public List<OtrosCertificados> buscarOtrosCertificadosEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT oc FROM OtrosCertificados oc WHERE oc.empleado.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            List<OtrosCertificados> certificados = (List<OtrosCertificados>) query.getResultList();
            return certificados;
        } catch (Exception e) {
            List<OtrosCertificados> certificados = null;
            return certificados;
        }
    }
}
