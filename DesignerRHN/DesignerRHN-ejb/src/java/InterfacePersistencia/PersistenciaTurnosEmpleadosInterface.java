/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaTurnosEmpleadosInterface {

    public Date obtenerFechaInicialMinimaTurnosEmpleados(EntityManager em);

    public Date obtenerFechaFinalMaximaTurnosEmpleados(EntityManager em);

    public int ejecutarPKG_CONTARNOVEDADESLIQ(EntityManager em,Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta);

    public void ejecutarPKG_ELIMINARLIQUIDACION(EntityManager em,Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta);

}
