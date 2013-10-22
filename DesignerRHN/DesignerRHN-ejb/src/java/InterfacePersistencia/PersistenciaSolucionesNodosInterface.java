/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SolucionesNodos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaSolucionesNodosInterface {
   public void crear(SolucionesNodos solucionNodo);
   public void editar(SolucionesNodos solucionNodo);
   public void borrar(SolucionesNodos solucionNodo);
   public SolucionesNodos buscarSolucionNodo(Object id);
   public List<SolucionesNodos> buscarSolucionesNodos();
   public List<SolucionesNodos> solucionNodoCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
   public List<SolucionesNodos> solucionNodoCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
   public BigDecimal diasProvisionados(BigInteger empl);
   public Long validacionTercerosVigenciaAfiliacion(BigInteger secuencia,Date fechaInicial,BigDecimal secuenciaTE, BigInteger secuenciaTer);
   public List<SolucionesNodos> solucionNodoEmpleado(BigInteger secuenciaEmpleado);
   public List<SolucionesNodos> solucionNodoEmpleador(BigInteger secuenciaEmpleado);
}
