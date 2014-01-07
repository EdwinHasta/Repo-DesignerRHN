/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import ClasesAyuda.ResultadoBorrarTodoNovedades;
import Entidades.ActualUsuario;
import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.FormulasConceptos;
import Entidades.Novedades;
import Entidades.TempNovedades;
import Entidades.VWActualesReformasLaborales;
import Entidades.VWActualesTiposContratos;
import Entidades.VWActualesTiposTrabajadores;
import InterfaceAdministrar.AdministrarCargueArchivosInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaFormulasConceptosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaFormulasNovedadesInterface;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaSolucionesFormulasInterface;
import InterfacePersistencia.PersistenciaTempNovedadesInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaVWActualesReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposContratosInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosRLInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosTCInterface;
import InterfacePersistencia.PersistenciaVigenciasConceptosTTInterface;
import InterfacePersistencia.PersistenciaVigenciasGruposConceptosInterface;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'CargueArchivos'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarCargueArchivos implements AdministrarCargueArchivosInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTempNovedades'.
     */
    @EJB
    PersistenciaTempNovedadesInterface persistenciaTempNovedades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaConceptos'.
     */
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaPeriodicidades'.
     */
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaTerceros'.
     */
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesTiposTrabajadores'.
     */
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesReformasLaborales'.
     */
    @EJB
    PersistenciaVWActualesReformasLaboralesInterface persistenciaVWActualesReformasLaborales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesTiposContratos'.
     */
    @EJB
    PersistenciaVWActualesTiposContratosInterface persistenciaVWActualesTiposContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaFormulasConceptos'.
     */
    @EJB
    PersistenciaFormulasConceptosInterface persistenciaFormulasConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaFormulasNovedades'.
     */
    @EJB
    PersistenciaFormulasNovedadesInterface persistenciaFormulasNovedades;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosRL'.
     */
    @EJB
    PersistenciaVigenciasConceptosRLInterface persistenciaVigenciasConceptosRL;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosTC'.
     */
    @EJB
    PersistenciaVigenciasConceptosTCInterface persistenciaVigenciasConceptosTC;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasConceptosTT'.
     */
    @EJB
    PersistenciaVigenciasConceptosTTInterface persistenciaVigenciasConceptosTT;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaFormulas'.
     */
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasGruposConceptos'.
     */
    @EJB
    PersistenciaVigenciasGruposConceptosInterface persistenciaVigenciasGruposConceptos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstructuras'.
     */
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaSolucionesFormulas'.
     */
    @EJB
    PersistenciaSolucionesFormulasInterface persistenciaSolucionesFormulas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaNovedades'.
     */
    @EJB
    PersistenciaNovedadesInterface persistenciaNovedades;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void crearTempNovedades(TempNovedades tnovedad) {
        persistenciaTempNovedades.crear(tnovedad);
    }

    @Override
    public void modificarTempNovedades(TempNovedades tnovedad) {
        persistenciaTempNovedades.editar(tnovedad);
    }

    @Override
    public void borrarRegistrosTempNovedades(String usuarioBD) {
        persistenciaTempNovedades.borrarRegistrosTempNovedades(usuarioBD);
    }

    @Override
    public ActualUsuario actualUsuario() {
        return persistenciaActualUsuario.actualUsuarioBD();
    }

    @Override
    public BigInteger empresaParametros(String usuarioBD) {
        return persistenciaParametrosEstructuras.buscarEmpresaParametros(usuarioBD);
    }

    @Override
    public List<TempNovedades> listaTempNovedades(String usuarioBD) {
        return persistenciaTempNovedades.obtenerTempNovedades(usuarioBD);
    }

    //PRIMERA ETAPA VALIDACION
    //VALIDACION DE CAMPOS

    @Override
    public boolean existenciaEmpleado(BigInteger codEmpleado, BigInteger secEmpresa) {
        return persistenciaEmpleado.verificarCodigoEmpleado_Empresa(codEmpleado, secEmpresa);
    }

    @Override
    public boolean existenciaConcepto(BigInteger codConcepto) {
        return persistenciaConceptos.verificarCodigoConcepto(codConcepto);
    }

    @Override
    public boolean existenciaPeriodicidad(BigInteger codPeriodicidad) {
        return persistenciaPeriodicidades.verificarCodigoPeriodicidad(codPeriodicidad);
    }

    @Override
    public boolean existenciaTercero(BigInteger nitTercero) {
        return persistenciaTerceros.verificarTerceroPorNit(nitTercero);
    }
    //SEGUNDA ETAPA VALIDACION    

    @Override
    public boolean validarTipoEmpleadoActivo(BigInteger codEmpleado, BigInteger secEmpresa) {
        Empleados empleado = persistenciaEmpleado.buscarEmpleadoCodigo_Empresa(codEmpleado, secEmpresa);
        return persistenciaVWActualesTiposTrabajadores.verificarTipoTrabajador(empleado);
    }

    @Override
    public Empleados buscarEmpleadoCodigo(BigInteger codEmpleado, BigInteger secEmpresa) {
        Empleados empleado = persistenciaEmpleado.buscarEmpleadoCodigo_Empresa(codEmpleado, secEmpresa);
        return empleado;
    }

    //BUSCAR EMPLEADO, ACTUAL TIPO TRABAJADOR, ACTUAL SUELDO, ACTUAL CONTRATO. 
    @Override
    public VWActualesTiposTrabajadores buscarTipoTrabajador(BigInteger secuenciaEmpleado) {
        return persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secuenciaEmpleado);
    }

    @Override
    public VWActualesReformasLaborales buscarActualReformaLaboral(BigInteger secuenciaEmpleado) {
        return persistenciaVWActualesReformasLaborales.buscarReformaLaboral(secuenciaEmpleado);
    }

    @Override
    public VWActualesTiposContratos buscarActualTipoContrato(BigInteger secuenciaEmpleado) {
        return persistenciaVWActualesTiposContratos.buscarTiposContratosEmpleado(secuenciaEmpleado);
    }
    //VALIDAD QUE EL CONCEPTO EXISTA EN LA EMPRESA

    @Override
    public Conceptos validarConceptoEmpresa(BigInteger codigoConcepto, BigInteger codigoEmpresa) {
        return persistenciaConceptos.validarCodigoConcepto(codigoConcepto, codigoEmpresa);
    }
    //DETERMINAR TIPO CONCEPTO

    @Override
    public String determinarTipoConcepto(BigInteger secConcepto) {
        String tipo = "MANUAL";
        boolean verificar = persistenciaFormulasConceptos.verificarExistenciaConceptoFormulasConcepto(secConcepto);
        if (verificar == true) {
            List<FormulasConceptos> formulasConcepto = persistenciaFormulasConceptos.formulasConcepto(secConcepto);
            for (int i = 0; i < formulasConcepto.size(); i++) {
                verificar = persistenciaFormulasNovedades.verificarExistenciaFormulasNovedades(formulasConcepto.get(i).getFormula().getSecuencia());
                if (verificar == true) {
                    tipo = "SEMI-AUTOMATICO";
                } else {
                    tipo = "AUTOMATICO";
                    break;
                }
            }
            return tipo;
        } else {
            tipo = "MANUAL";
            return tipo;
        }
    }

    @Override
    public boolean verificarZonaT(BigInteger secConcepto, BigInteger secRL, BigInteger secTC, BigInteger secTT) {
        try {
            boolean validarZonaT;
            validarZonaT = persistenciaVigenciasConceptosRL.verificacionZonaTipoReformasLaborales(secConcepto, secRL);
            if (validarZonaT == true) {
                validarZonaT = persistenciaVigenciasConceptosTC.verificacionZonaTipoContrato(secConcepto, secTC);
                if (validarZonaT == true) {
                    validarZonaT = persistenciaVigenciasConceptosTT.verificacionZonaTipoTrabajador(secConcepto, secTT);
                }
            }
            return validarZonaT;
        } catch (Exception e) {
            System.out.println("Error verificarZonaT: " + e);
            return false;
        }
    }
    //FORMULAS VALIDAS PARA EL CARGUE

    @Override
    public List<Formulas> formulasCargue() {
        return persistenciaFormulas.buscarFormulasCarge();
    }

    @Override
    public Formulas formulaCargueInicial() {
        return persistenciaFormulas.buscarFormulaCargeInicial();
    }

    @Override
    public boolean validarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula) {
        return persistenciaFormulasConceptos.verificarFormulaCargue_Concepto(secConcepto, secFormula);
    }

    @Override
    public boolean validarNecesidadTercero(BigInteger secConcepto) {
        return persistenciaVigenciasGruposConceptos.verificacionGrupoUnoConcepto(secConcepto);
    }

    @Override
    public boolean validarTerceroEmpresaEmpleado(BigInteger nit, BigInteger secEmpresa) {
        return persistenciaTerceros.verificarTerceroParaEmpresaEmpleado(nit, secEmpresa);
    }

    @Override
    public List<String> obtenerDocumentosSoporteCargados(String usuarioBD) {
        return persistenciaTempNovedades.obtenerDocumentosSoporteCargados(usuarioBD);
    }

    @Override
    public void cargarTempNovedades(Date fechaReporte, String nombreCorto, String usarFormula) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaR = formatoFecha.format(fechaReporte);
        persistenciaTempNovedades.cargarTempNovedades(fechaR, nombreCorto, usarFormula);
    }

    @Override
    public int reversar(ActualUsuario usuarioBD, String documentoSoporte) {
        List<Novedades> listNovedades = persistenciaNovedades.novedadesParaReversar(usuarioBD.getSecuencia(), documentoSoporte);
        int validarNoLiquidadas = 0;
        for (int i = 0; i < listNovedades.size(); i++) {
            if (persistenciaSolucionesFormulas.validarNovedadesNoLiquidadas(listNovedades.get(i).getSecuencia()) > 0) {
                validarNoLiquidadas++;
            }
        }
        listNovedades.clear();
        if (validarNoLiquidadas == 0) {
            persistenciaTempNovedades.reversarTempNovedades(usuarioBD.getAlias(), documentoSoporte);
            return persistenciaNovedades.reversarNovedades(usuarioBD.getSecuencia(), documentoSoporte);
        } else {
            return 0;
        }
    }

    @Override
    public ResultadoBorrarTodoNovedades BorrarTodo(ActualUsuario usuarioBD, List<String> documentosSoporte) {
        ResultadoBorrarTodoNovedades resultadoProceso = new ResultadoBorrarTodoNovedades();
        int registrosBorrados = 0;
        for (int j = 0; j < documentosSoporte.size(); j++) {
            List<Novedades> listNovedades = persistenciaNovedades.novedadesParaReversar(usuarioBD.getSecuencia(), documentosSoporte.get(j));
            int validarNoLiquidadas = 0;
            for (int i = 0; i < listNovedades.size(); i++) {
                if (persistenciaSolucionesFormulas.validarNovedadesNoLiquidadas(listNovedades.get(i).getSecuencia()) > 0) {
                    validarNoLiquidadas++;
                }
            }
            listNovedades.clear();
            if (validarNoLiquidadas == 0) {
                registrosBorrados = registrosBorrados + persistenciaNovedades.reversarNovedades(usuarioBD.getSecuencia(), documentosSoporte.get(j));
                persistenciaTempNovedades.reversarTempNovedades(usuarioBD.getAlias(), documentosSoporte.get(j));
            } else {
                resultadoProceso.getDocumentosNoBorrados().add(documentosSoporte.get(j));
            }
        }
        resultadoProceso.setRegistrosBorrados(registrosBorrados);
        return resultadoProceso;
    }
}
