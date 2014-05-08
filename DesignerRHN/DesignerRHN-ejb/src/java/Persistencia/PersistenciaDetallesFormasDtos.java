/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.DetallesFormasDtos;
import InterfacePersistencia.PersistenciaDetallesFormasDtosInterface;
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
public class PersistenciaDetallesFormasDtos implements PersistenciaDetallesFormasDtosInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,DetallesFormasDtos detallesFormasDtos) {
        try {
            em.merge(detallesFormasDtos);
        } catch (Exception e) {
            System.out.println("El detallesFormasDtos no exite o esta reservada por lo cual no puede ser modificada (detallesFormasDtos)");
        }
    }

    @Override
    public void editar(EntityManager em,DetallesFormasDtos detallesFormasDtos) {
        try {
            em.merge(detallesFormasDtos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el detallesFormasDtos");
        }
    }

    @Override
    public void borrar(EntityManager em,DetallesFormasDtos detallesFormasDtos) {
        try {
            em.remove(em.merge(detallesFormasDtos));
        } catch (Exception e) {
            System.out.println("El detallesFormasDtos no se ha podido eliminar");
        }
    }

    @Override
    public List<DetallesFormasDtos> detallesFormasDescuentos(EntityManager em,BigInteger formaDto) {
        try {
            Query query = em.createQuery("SELECT dfd FROM DetallesFormasDtos dfd WHERE dfd.formadto.secuencia = :formaDto ORDER BY 2");
            query.setParameter("formaDto", formaDto);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<DetallesFormasDtos> detallesFormasDtos = query.getResultList();
            List<DetallesFormasDtos> detallesFormasDtosResult = new ArrayList<DetallesFormasDtos>(detallesFormasDtos);
            return detallesFormasDtosResult;
        } catch (Exception e) {
            System.out.println("Error: (eersPrestamos)" + e);
            return null;
        }
    }
}
