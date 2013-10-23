/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaVWAcumuladosInterface;
import Entidades.VWAcumulados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaVWAcumulados implements PersistenciaVWAcumuladosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<VWAcumulados> buscarVigenciasNormasEmpleadosPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vwa FROM VWAcumulados vwa WHERE vwa.empleado.secuencia = :secuenciaEmpl ORDER BY vwa.fechaPago DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<VWAcumulados> VWAcumuladosPorEmpleado = query.getResultList();
            return VWAcumuladosPorEmpleado;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VWAcumulados " + e);
            return null;
        }
    }
}
