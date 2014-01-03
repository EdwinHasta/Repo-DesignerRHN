/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ReformasLaborales;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br> 
 * Clase encargada de realizar operaciones sobre la tabla 'ReformasLaborales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaReformasLaborales implements PersistenciaReformasLaboralesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ReformasLaborales reformaLaboral) {
        em.persist(reformaLaboral);
    }

    @Override
    public void editar(ReformasLaborales reformaLaboral) {
        em.merge(reformaLaboral);
    }

    @Override
    public void borrar(ReformasLaborales reformaLaboral) {
        em.remove(em.merge(reformaLaboral));
    }

    @Override
    public List<ReformasLaborales> buscarReformasLaborales() {
        List<ReformasLaborales> reformaLista = (List<ReformasLaborales>) em.createNamedQuery("ReformasLaborales.findAll")
                .getResultList();
        return reformaLista;
    }

    @Override
    public ReformasLaborales buscarReformaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT e FROM ReformasLaborales e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ReformasLaborales reformaL = (ReformasLaborales) query.getSingleResult();
            return reformaL;
        } catch (Exception e) {
        }
        ReformasLaborales reformaL = null;
        return reformaL;
    }
}
