/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.DetallesFormulas;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaDetallesFormulasInterface;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaParametrosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Comprobantes'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarComprobantes implements AdministrarComprobantesInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaSolucionesNodos'.
     */
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametros'.
     */
    @EJB
    PersistenciaParametrosInterface persistenciaParametros;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstructuras'.
     */
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaDetallesFormulas'.
     */
    @EJB
    PersistenciaDetallesFormulasInterface persistenciaDetallesFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaHistoriasformulas'.
     */
    @EJB
    PersistenciaHistoriasformulasInterface persistenciaHistoriasformulas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    @Override
    public List<Parametros> consultarParametrosComprobantesActualUsuario() {
        String usuarioBD;
        usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaParametros.parametrosComprobantes(usuarioBD);
    }

    @Override
    public ParametrosEstructuras consultarParametroEstructuraActualUsuario() {
        String usuarioBD;
        usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaParametrosEstructuras.buscarParametro(usuarioBD);
    }
    
    @Override
    public List<SolucionesNodos> consultarSolucionesNodosEmpleado(BigInteger secEmpleado){
        return persistenciaSolucionesNodos.solucionNodoEmpleado(secEmpleado);
    }
    
    @Override
    public List<SolucionesNodos> consultarSolucionesNodosEmpleador(BigInteger secEmpleado){
        return persistenciaSolucionesNodos.solucionNodoEmpleador(secEmpleado);
    }
    
    @Override
    public List<DetallesFormulas> consultarDetallesFormulasEmpleado(BigInteger secEmpleado, String fechaDesde, String fechaHasta, BigInteger secProceso, BigInteger secHistoriaFormula){
        return persistenciaDetallesFormulas.detallesFormula(secEmpleado, fechaDesde, fechaHasta, secProceso, secHistoriaFormula);
    }
    
    @Override
    public BigInteger consultarHistoriaFormulaFormula(BigInteger secFormula, String fechaDesde){
        return persistenciaHistoriasformulas.obtenerSecuenciaHistoriaFormula(secFormula, fechaDesde);
    }
}
