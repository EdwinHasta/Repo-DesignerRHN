package Persistencia;

import Entidades.Empresas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import javax.persistence.Query;

@Stateless
public class PersistenciaEmpresas implements PersistenciaEmpresasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear empresa.
     */
    public void crear(Empresas empresas) {
        try {
            em.persist(empresas);
        } catch (Exception e) {
            System.out.println("No es posible crear la empresa");
        }
    }

    /*
     *Editar empresa. 
     */
    public void editar(Empresas empresas) {
        try {
            em.merge(empresas);
        } catch (Exception e) {
            System.out.println("La empresa no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    /*
     *Borrar empresa.
     */
    public void borrar(Empresas empresas) {
        em.remove(em.merge(empresas));
    }

    /*
     *Encontrar una empresa. 
     */
    public Empresas buscarEmpresa(Object id) {
        try {
            return em.find(Empresas.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las empresas.
     */
    public List<Empresas> buscarEmpresas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Empresas.class));
        return em.createQuery(cq).getResultList();
    }

    public Empresas buscarEmpresasSecuencia(BigInteger secuencia) {
        Empresas empresas;
        try {
            Query query = em.createQuery("SELECT e FROM Empresas e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            empresas = (Empresas) query.getSingleResult();
            return empresas;
        } catch (Exception e) {
            empresas = null;
            System.out.println("Error buscarEmpresasSecuencia PersistenciaEmpresas");
            return empresas;
        }

    }
}
