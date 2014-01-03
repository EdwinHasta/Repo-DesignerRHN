/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Departamentos;
import InterfacePersistencia.PersistenciaDepartamentosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Departamentos'
 * de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDepartamentos implements PersistenciaDepartamentosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<Departamentos> departamentos() {
        try {
            Query query = em.createQuery("SELECT d FROM Departamentos d ORDER BY d.nombre");
            List<Departamentos> departamentos = query.getResultList();
            return departamentos;
        } catch (Exception e) {
            return null;
        }
    }
}



