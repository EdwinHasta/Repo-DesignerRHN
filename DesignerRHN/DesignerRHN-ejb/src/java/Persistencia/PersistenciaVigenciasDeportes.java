package Persistencia;

import Entidades.VigenciasDeportes;
import InterfacePersistencia.PersistenciaVigenciasDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasDeportes implements PersistenciaVigenciasDeportesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasDeportes vigenciasDeportes) {
        try {
            em.persist(vigenciasDeportes);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasDeportes)");
        }
    }

    @Override
    public void editar(VigenciasDeportes vigenciasDeportes) {
        try {
            em.merge(vigenciasDeportes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la VigenciasDeportes");
        }
    }

    @Override
    public void borrar(VigenciasDeportes vigenciasDeportes) {
        try {
            em.remove(em.merge(vigenciasDeportes));
        } catch (Exception e) {
            System.out.println("la VigenciasDeportes no se ha podido eliminar");
        }
    }

    @Override
    public List<VigenciasDeportes> deportePersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vd) FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vd FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona and vd.fechainicial = (SELECT MAX(vde.fechainicial) FROM VigenciasDeportes vde WHERE vde.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasDeportes> listaVigenciasDeportes = queryFinal.getResultList();
                return listaVigenciasDeportes;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasDeportes.deportePersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasDeportes> deportesTotalesSecuenciaPersona(BigInteger secuenciaP) {
        try {
            Query queryFinal = em.createQuery("SELECT vd FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona");
            queryFinal.setParameter("secuenciaPersona", secuenciaP);
            List<VigenciasDeportes> listaVigenciasDeportes = queryFinal.getResultList();
            return listaVigenciasDeportes;
        } catch (Exception e) {
            System.out.println("Error deportesTotalesSecuenciaPersona PersistenciaVigenciasDeportes : " + e.toString());
            return null;
        }
    }
}
