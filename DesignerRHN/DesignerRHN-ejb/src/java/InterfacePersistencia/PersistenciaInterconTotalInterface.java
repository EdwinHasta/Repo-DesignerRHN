/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.InterconTotal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaInterconTotalInterface {

    public void crear(EntityManager em, InterconTotal interconTotal);

    public void editar(EntityManager em, InterconTotal interconTotal);

    public void borrar(EntityManager em, InterconTotal interconTotal);

    public InterconTotal buscarInterconTotalSecuencia(EntityManager em, BigInteger secuencia);

    public List<InterconTotal> buscarInterconTotalParaParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal);

}
