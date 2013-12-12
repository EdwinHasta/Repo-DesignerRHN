/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empresas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import java.math.BigInteger;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Empresas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEmpresas implements PersistenciaEmpresasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Empresas empresas) {
        try {
            em.persist(empresas);
        } catch (Exception e) {
            System.out.println("No es posible crear la empresa");
        }
    }

    @Override
    public void editar(Empresas empresas) {
        try {
            em.merge(empresas);
        } catch (Exception e) {
            System.out.println("La empresa no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(Empresas empresas) {
        em.remove(em.merge(empresas));
    }

    @Override
    public List<Empresas> buscarEmpresas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Empresas.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
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
    
//PANTALLA BARRA CONSULTAR DATOS DESPUES DE LIQUIDAR

    @Override
    public String estadoConsultaDatos(BigInteger secuenciaEmpresa) {
        try {
            Query query = em.createQuery("SELECT e.barraconsultadatos FROM Empresas e WHERE e.secuencia = :secuenciaEmpresa");
            query.setParameter("secuenciaEmpresa", secuenciaEmpresa);
            String estado = (String) query.getSingleResult();
            return estado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaEmpresas.estadoConsultaDatos");
            return null;
        }
    }

    @Override
    public String nombreEmpresa(EntityManager entity) {
        try {
            Query query = entity.createQuery("SELECT COUNT(e) FROM Empresas e WHERE e.codigo > 0");
            Long resultado = (Long) query.getSingleResult();
            if (resultado == 1) {
                query = entity.createQuery("SELECT e.nombre FROM Empresas e WHERE e.codigo > 0");
                String nombreE = (String) query.getSingleResult();
                return nombreE;
            } else if (resultado > 1) {
                return "(MULTIEMPRESA)";
            } else {
                return "SIN REGISTRAR";
            }
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaEmpleados.nombreEmpresa" + e);
            return null;
        }
    }
}
