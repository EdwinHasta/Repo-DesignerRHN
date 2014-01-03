/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSoCondicionesAmbientalesPInterface;
import Entidades.SoCondicionesAmbientalesP;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'SoCondicionesAmbientalesP'
 * de la base de datos.
 * @author John Pineda.
 */
@Stateless
public class PersistenciaSoCondicionesAmbientalesP implements PersistenciaSoCondicionesAmbientalesPInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.persist(soCondicionesAmbientalesP);
    }

    @Override
    public void editar(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.merge(soCondicionesAmbientalesP);
    }

    @Override
    public void borrar(SoCondicionesAmbientalesP soCondicionesAmbientalesP) {
        em.remove(em.merge(soCondicionesAmbientalesP));
    }

    @Override
    public SoCondicionesAmbientalesP buscarSoCondicionAmbientalP(BigInteger secuencia) {
        try {
            return em.find(SoCondicionesAmbientalesP.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SoCondicionesAmbientalesP> buscarSoCondicionesAmbientalesP() {
        try {
            Query query = em.createQuery("SELECT l FROM SoCondicionesAmbientalesP  l ORDER BY l.codigo ASC ");
            List<SoCondicionesAmbientalesP> listSoCondicionesAmbientalesP = query.getResultList();
            return listSoCondicionesAmbientalesP;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR CLASES SO CONDICIONES AMBIENTALES P :" + e);
            return null;
        }

    }

    @Override
    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos sam WHERE sam.condicionambientalp = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = (BigInteger) query.getSingleResult();
            System.err.println("Contador PersistenciaSoCondicionesAmbientalesP contadorSoAccidentesMedicos persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaSoCondicionesAmbientalesP contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }
}
