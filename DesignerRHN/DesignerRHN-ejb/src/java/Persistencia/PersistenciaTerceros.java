package Persistencia;

import Entidades.Terceros;
import InterfacePersistencia.PersistenciaTercerosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaTerceros implements PersistenciaTercerosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Terceros terceros) {
        try {
            em.persist(terceros);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTerceros");
        }
    }

    @Override
    public void editar(Terceros terceros) {
        try {
            em.merge(terceros);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTerceros");
        }
    }

    @Override
    public void borrar(Terceros terceros) {
        try {
            em.remove(em.merge(terceros));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTerceros");
        }
    }

    @Override
    public Terceros buscarTercero(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Terceros.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTercero PersistenciaTerceros");
            return null;
        }

    }


    public List<Terceros> buscarTerceros() {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t, Empresas e, TercerosSucursales ts  WHERE t.secuencia = ts.tercero.secuencia AND t.empresa.secuencia = e.secuencia");
            List<Terceros> terceros = (List<Terceros>) query.getResultList();
            return terceros;
        } catch (Exception e) {
            System.out.println("Error buscarTerceros");
            return null;
        }
    }

    @Override
    public Terceros buscarTercerosSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Terceros terceros = (Terceros) query.getSingleResult();
            return terceros;
        } catch (Exception e) {
            System.out.println("Error buscarTercerosSecuencia");
            Terceros terceros = null;
            return terceros;
        }

    }

    @Override
    public boolean verificarTerceroPorNit(BigInteger nit) {
        try {
            Query query = em.createQuery("SELECT COUNT(t) FROM Terceros t WHERE t.nit = :nit");
            query.setParameter("nit", nit);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public boolean verificarTerceroParaEmpresaEmpleado(BigInteger nit, BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT COUNT(t) FROM Terceros t WHERE t.nit = :nit AND t.empresa.secuencia = :secEmpresa");
            query.setParameter("nit", nit);
            query.setParameter("secEmpresa", secEmpresa);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }
    
    @Override
    public List<Terceros> lovTerceros(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT t FROM Terceros t WHERE t.empresa.secuencia = :secEmpresa ORDER BY t.nombre");
            query.setParameter("secEmpresa", secEmpresa);
            List<Terceros> listaTerceros = query.getResultList();
            return listaTerceros;
        } catch (Exception e) {
            System.out.println("Exepcion lovTerceros: " + e);
            return null;
        }
    }
}
