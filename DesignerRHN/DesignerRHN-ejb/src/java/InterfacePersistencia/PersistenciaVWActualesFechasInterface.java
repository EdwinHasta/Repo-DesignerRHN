/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.VWActualesFechas;
import java.util.Date;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaVWActualesFechasInterface {

    public Date actualFechaHasta(EntityManager em);

    public Date actualFechaDesde(EntityManager em);
}
