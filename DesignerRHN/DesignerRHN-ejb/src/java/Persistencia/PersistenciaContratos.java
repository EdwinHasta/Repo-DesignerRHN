
package Persistencia;

import Entidades.Contratos;
import InterfacePersistencia.PersistenciaContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author AndresPineda
 */

@Stateless
public class PersistenciaContratos implements PersistenciaContratosInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Contrato.
     */
    @Override
    public void crear(Contratos contratos) {
        em.persist(contratos);
    }

    /*
     *Editar Contrato. 
     */
    public void editar(Contratos contratos) {
        em.merge(contratos);
    }

    /*
     *Borrar Contrato.
     */
    @Override
    public void borrar(Contratos contratos) {
        em.remove(em.merge(contratos));
    }

    /*
     *Encontrar un Contratol. 
     */
    @Override
    public Contratos buscarContrato(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Contratos.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todos los Contratos. 
     */
    @Override
    public List<Contratos> buscarContratos() {


        List<Contratos> contratoLista = (List<Contratos>) em.createNamedQuery("Contratos.findAll")
                .getResultList();
        return contratoLista;
    }

    @Override
    public Contratos buscarContratoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT e FROM Contratos e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Contratos contrato = (Contratos) query.getSingleResult();
            return contrato;
        } catch (Exception e) {
            Contratos contrato = null;
            return contrato;
        }
        
    }

}
