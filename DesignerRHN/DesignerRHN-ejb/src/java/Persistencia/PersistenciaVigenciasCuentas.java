/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasCuentas;
import InterfacePersistencia.PersistenciaVigenciasCuentasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasCuentas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasCuentas implements PersistenciaVigenciasCuentasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasCuentas vigenciasCuentas) {
        try {
            em.persist(vigenciasCuentas);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaCuenta Persistencia : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasCuentas vigenciasCuentas) {
        try {
            em.merge(vigenciasCuentas);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaCuenta Persistencia : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasCuentas vigenciasCuentas) {
        try {
            em.remove(em.merge(vigenciasCuentas));
        } catch (Exception e) {
            System.out.println("Error crearVigenciaCuenta Persistencia : " + e.toString());
        }
    }

    @Override
    public List<VigenciasCuentas> buscarVigenciasCuentas() {
        List<VigenciasCuentas> vigenciasCuentas = (List<VigenciasCuentas>) em.createNamedQuery("VigenciasCuentas.findAll").getResultList();
        return vigenciasCuentas;
    }

    @Override
    public VigenciasCuentas buscarVigenciaCuentaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCuentas vc WHERE vc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            VigenciasCuentas vigenciasCuentas = (VigenciasCuentas) query.getSingleResult();
            return vigenciasCuentas;
        } catch (Exception e) {
            VigenciasCuentas vigenciasCuentas = null;
            return vigenciasCuentas;
        }
    }

    @Override
    public List<VigenciasCuentas> buscarVigenciasCuentasPorCredito(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCuentas vc WHERE vc.cuentac.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            List<VigenciasCuentas> vigenciasCuentas = (List<VigenciasCuentas>) query.getResultList();
            return vigenciasCuentas;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCuentasPorCredito Persistencia : " + e.toString());
            List<VigenciasCuentas> vigenciasCuentas = null;
            return vigenciasCuentas;
        }
    }

    @Override
    public List<VigenciasCuentas> buscarVigenciasCuentasPorDebito(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCuentas vc WHERE vc.cuentad.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            List<VigenciasCuentas> vigenciasCuentas = (List<VigenciasCuentas>) query.getResultList();
            return vigenciasCuentas;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCuentasPorDebito Persistencia : " + e.toString());
            List<VigenciasCuentas> vigenciasCuentas = null;
            return vigenciasCuentas;
        }
    }
    
    @Override
    public List<VigenciasCuentas> buscarVigenciasCuentasPorConcepto(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCuentas vc WHERE vc.concepto.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            List<VigenciasCuentas> vigenciasCuentas = (List<VigenciasCuentas>) query.getResultList();
            return vigenciasCuentas;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCuentasPorConcepto Persistencia : " + e.toString());
            List<VigenciasCuentas> vigenciasCuentas = null;
            return vigenciasCuentas;
        }
    }
}
