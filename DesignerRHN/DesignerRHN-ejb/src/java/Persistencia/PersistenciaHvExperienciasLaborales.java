package Persistencia;

import Entidades.HvExperienciasLaborales;
import InterfacePersistencia.PersistenciaHvExperienciasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaHvExperienciasLaborales implements PersistenciaHvExperienciasLaboralesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(HvExperienciasLaborales experienciasLaborales) {
        em.persist(experienciasLaborales);
    }

    @Override
    public void editar(HvExperienciasLaborales experienciasLaborales) {
        em.merge(experienciasLaborales);
    }

    @Override
    public void borrar(HvExperienciasLaborales experienciasLaborales) {
        em.remove(em.merge(experienciasLaborales));
    }

    @Override
    public HvExperienciasLaborales buscarHvExperienciaLaboral(BigInteger secuencia) {
        try {
            return em.find(HvExperienciasLaborales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la PersistenciaHvExperienciasLaborales ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<HvExperienciasLaborales> experienciaLaboralPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hve) FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hve FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV and hve.fechadesde = (SELECT MAX(hvex.fechadesde) FROM HvExperienciasLaborales hvex WHERE hvex.hojadevida.secuencia = :secuenciaHV)");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvExperienciasLaborales> listaExperienciasLaborales = queryFinal.getResultList();
                return listaExperienciasLaborales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvExperienciasLaborales.experienciaLaboralPersona" + e);
            return null;
        }
    }

    @Override
    public List<HvExperienciasLaborales> experienciasLaboralesSecuenciaEmpleado(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT hve FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            queryFinal.setParameter("secuenciaHV", secuencia);
            List<HvExperienciasLaborales> listaExperienciasLaborales = queryFinal.getResultList();
            return listaExperienciasLaborales;
        } catch (Exception e) {
            System.out.println("Error experienciasLaboralesSecuenciaEmpleado PersistenciaHvExperienciasLaborales : "+e.toString());
            return null;
        }
    }
}
