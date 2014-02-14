/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Operandos;
import InterfacePersistencia.PersistenciaOperandosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Operandos'
 * de la base de datos.
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaOperandos implements PersistenciaOperandosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<Operandos> buscarOperandos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operandos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarOperandos PersistenciaOperandos : "+e.toString());
            return null;
        }
    }
    
    @Override
    public void crear(Operandos operandos) {
        try {
            em.persist(operandos);
        } catch (Exception e) {
            System.out.println("El Operandos no exite o esta reservada por lo cual no puede ser modificada (Operandos)");
        }
    }

    @Override
    public void editar(Operandos operandos) {
        try {
            em.merge(operandos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Operandos");
        }
    }

    @Override
    public void borrar(Operandos operandos) {
        try {
            em.remove(em.merge(operandos));
        } catch (Exception e) {
            System.out.println("El Operandos no se ha podido eliminar");
        }
    }
}
