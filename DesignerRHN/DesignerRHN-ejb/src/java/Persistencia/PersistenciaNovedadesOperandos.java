/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.NovedadesOperandos;
import InterfacePersistencia.PersistenciaNovedadesOperandosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class PersistenciaNovedadesOperandos implements PersistenciaNovedadesOperandosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(NovedadesOperandos novedadesOperandos) {
        try {
            em.merge(novedadesOperandos);
        } catch (Exception e) {
            System.out.println("El novedadesOperandos no exite o esta reservada por lo cual no puede ser modificada (novedadesOperandos)");
        }
    }

    @Override
    public void editar(NovedadesOperandos novedadesOperandos) {
        try {
            em.merge(novedadesOperandos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el novedadesOperandos");
        }
    }

    @Override
    public void borrar(NovedadesOperandos novedadesOperandos) {
        try {
            em.remove(em.merge(novedadesOperandos));
        } catch (Exception e) {
            System.out.println("El novedadesOperandos no se ha podido eliminar");
        }
    }

    @Override
    public List<NovedadesOperandos> novedadesOperandos(BigInteger secuenciaOperando) {
        try {
            Query query = em.createQuery("SELECT no FROM NovedadesOperandos no WHERE no.operando.secuencia =:secuenciaOperando");
            query.setParameter("secuenciaOperando", secuenciaOperando);
            List<NovedadesOperandos> novedadesOperandos = query.getResultList();
            List<NovedadesOperandos> novedadesOperandosResult = new ArrayList<NovedadesOperandos>(novedadesOperandos);
            return novedadesOperandosResult;
        } catch (Exception e) {
            System.out.println("Error: (novedadesOperandos)" + e);
            return null;
        }
    }

}
