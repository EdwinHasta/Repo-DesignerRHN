package Cargue;

import ClasesAyuda.ErroresNovedades;
import ClasesAyuda.ResultadoBorrarTodoNovedades;
import Entidades.ActualUsuario;
import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.TempNovedades;
import Entidades.VWActualesReformasLaborales;
import Entidades.VWActualesTiposContratos;
import Entidades.VWActualesTiposTrabajadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCargueArchivosInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class CargarArchivoPlano implements Serializable {

    @EJB
    AdministrarCargueArchivosInterface administrarCargueArchivos;
    private List<TempNovedades> listTempNovedades;
    private List<TempNovedades> filtrarListTempNovedades;
    private TempNovedades novedadTablaSeleccionada;
    private TempNovedades editarNovedad;
    private HashSet hs;
    private TempNovedades tNovedades;
    private ActualUsuario UsuarioBD;
    private List<ErroresNovedades> listErrores;
    private ErroresNovedades erroresNovedad;
    //Formulas para el cargue
    private List<Formulas> listaFormulas;
    private List<Formulas> filtradoFormulas;
    private Formulas formulaSelecionada;
    private Formulas formulaUsada;
    //Otros
    private boolean aceptar;
    private UploadedFile file;
    //USAR FORMULA CONCEPTO
    private String usarFormulaConcepto;
    private String nombreCorto;
    //INPUT NOMBRE ARCHIVO
    private String nombreArchivoPlano;
    //SUBTOTAL CONCEPTOS MANUALES
    public BigDecimal subTotal;
    //ACTIVAR - DESACTIVAR BOTONES
    private boolean botones;
    private boolean cargue;
    //REVERSAR 
    private String documentoSoporteReversar;
    private List<String> documentosSoporteCargados;
    private List<String> filtradoDocumentosSoporteCargados;
    private String seleccionDocumentosSoporteCargado;
    private int resultado;
    //CARGAR
    private int diferenciaRegistrosN;
    //BORRAR TODO PICKLIST
    private DualListModel<String> documentosSoportes;
    private List<String> documentosEscogidos;
    private ResultadoBorrarTodoNovedades resultadoProceso;
    //ACTUALIZAR COLLECTION
    Collection<String> elementosActualizar;
    //
    private String infoRegistroFormula, infoRegistroDocumento, infoRegistro;
    //
    private String altoTabla;
    //
    private int bandera, tipoLista;
    //
    private Column columnaUnidadFraccion, columnaUnidadEntera, columnaSaldo, columnaTercero, columnaPeriodicidad, columnaValorTotal, columnaDocumentoSoporte, columnaFechaReporte, columnaFechaFinal, columnaFechaInicial, columnaEmpleado, columnaConcepto;
    //
    private int index, cualCelda;

    public CargarArchivoPlano() {
        novedadTablaSeleccionada = null;
        cualCelda = -1;
        editarNovedad = new TempNovedades();
        bandera = 0;
        tipoLista = 0;
        altoTabla = "130";
        tNovedades = new TempNovedades();
        //listTempNovedades = new ArrayList<TempNovedades>();
        hs = new HashSet();
        listErrores = new ArrayList<ErroresNovedades>();
        erroresNovedad = new ErroresNovedades();
        aceptar = true;
        usarFormulaConcepto = "S";
        subTotal = new BigDecimal(0);
        botones = false;
        cargue = true;
        documentosSoporteCargados = null;
        //PICKLIST
        documentosSoportes = null;
        documentosEscogidos = new ArrayList<String>();
        resultadoProceso = new ResultadoBorrarTodoNovedades();
        elementosActualizar = new ArrayList<String>();

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarCargueArchivos.obtenerConexion(ses.getId());
            listTempNovedades = null;
            getListTempNovedades();
            contarRegistros();
        } catch (Exception e) {
            System.out.println("Error postconstruct CargarArchivoPlano: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void editarCelda() {
        if (novedadTablaSeleccionada != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoLista == 0) {
                editarNovedad = novedadTablaSeleccionada;
            } else {
                editarNovedad = novedadTablaSeleccionada;
            }
            if (cualCelda == 0) {//Concepto
                context.update("formDialogos:editarConcepto");
                context.execute("editarConcepto.show()");
            } else if (cualCelda == 1) {//Empleado
                context.update("formDialogos:editarEmpleado");
                context.execute("editarEmpleado.show()");
            } else if (cualCelda == 2) {//Fecha inicial
                context.update("formDialogos:editarFechainicial");
                context.execute("editarFechainicial.show()");
            } else if (cualCelda == 3) {//Fecha final
                context.update("formDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
            } else if (cualCelda == 4) {//fecha reporte
                context.update("formDialogos:editarFechaReporte");
                context.execute("editarFechaReporte.show()");
            } else if (cualCelda == 5) {//documento soporte
                context.update("formDialogos:editarDocumentoS");
                context.execute("editarDocumentoS.show()");
            } else if (cualCelda == 6) {//valor
                context.update("formDialogos:editarValor");
                context.execute("editarValor.show()");
            } else if (cualCelda == 7) {//periodicidad
                context.update("formDialogos:editarPeriodicidad");
                context.execute("editarPeriodicidad.show()");
            } else if (cualCelda == 8) {//tercero
                context.update("formDialogos:editarTercero");
                context.execute("editarTercero.show()");
            } else if (cualCelda == 9) {//saldo
                context.update("formDialogos:editarSaldo");
                context.execute("editarSaldo.show()");
            } else if (cualCelda == 10) {//Unidad Fraccion
                context.update("formDialogos:editarUEntera");
                context.execute("editarUEntera.show()");
            } else if (cualCelda == 11) {//Unidad Fraccion
                context.update("formDialogos:editarUFraccion");
                context.execute("editarUFraccion.show()");
            }
        }
    }

    public void posicionTablaNovedad() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        index = Integer.parseInt(type);
        cualCelda = Integer.parseInt(name);
    }

    public void cargarArchivo(FileUploadEvent event) throws IOException {
        if (event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".") + 1).equalsIgnoreCase("prn")) {
            nombreArchivoPlano = event.getFile().getFileName();
            transformarArchivo(event.getFile().getSize(), event.getFile().getInputstream(), event.getFile().getFileName());
            modificarInfoRegistro(listTempNovedades.size());
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:errorExtensionArchivo");
            context.execute("errorExtensionArchivo.show()");
        }
    }

    public void transformarArchivo(long size, InputStream in, String nombreArchivo) {
        try {
            if (nombreArchivo.length() <= 30) {
                String destino = "C:\\Prueba\\Archivos_Planos_Cargados\\" + nombreArchivo;

                OutputStream out = new FileOutputStream(new File(destino));
                int reader = 0;
                byte[] bytes = new byte[(int) size];
                while ((reader = in.read(bytes)) != -1) {
                    out.write(bytes, 0, reader);
                }
                in.close();
                out.flush();
                out.close();
                leerTxt(destino, nombreArchivo);
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:errorNombreArchivo");
                context.execute("errorNombreArchivo.show()");
            }
        } catch (Exception e) {
            System.out.println("Error transformarArchivo Controlador : " + e.toString());
        }
    }

    public void leerTxt(String locArchivo, String nombreArchivo) throws FileNotFoundException, IOException {
        try {
            File archivo = new File(locArchivo);
            FileReader fr = new FileReader(archivo);
            BufferedReader bf = new BufferedReader(fr);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String sCadena;
            RequestContext context = RequestContext.getCurrentInstance();

            listTempNovedades.clear();
            listErrores.clear();

            while ((sCadena = bf.readLine()) != null) {
                tNovedades = new TempNovedades();

                //CONCEPTO
                String sConcepto = sCadena.substring(1, 8).trim();
                if (!sConcepto.equals("")) {
                    try {
                        BigInteger concepto = new BigInteger(sConcepto);
                        tNovedades.setConcepto(concepto);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setConcepto(null);
                }
                //EMPLEADO
                String sEmpleado = sCadena.substring(8, 19).trim();
                if (!sEmpleado.equals("")) {
                    try {
                        BigInteger empleado = new BigInteger(sEmpleado);
                        tNovedades.setEmpleado(empleado);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setEmpleado(null);
                }
                //FECHA INICIAL
                String fechaInicial = sCadena.substring(19, 29).trim();
                if (!fechaInicial.equals("")) {
                    if (fechaInicial.indexOf("-") > 0) {
                        fechaInicial = fechaInicial.replaceAll("-", "/");
                    }
                    try {
                        Date FechaInicial = formato.parse(fechaInicial);
                        tNovedades.setFechainicial(FechaInicial);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setFechainicial(null);
                }
                //FECHA FINAL
                String fechaFinal = sCadena.substring(29, 39).trim();
                if (!fechaFinal.equals("")) {
                    if (fechaFinal.indexOf("-") > 0) {
                        fechaFinal = fechaFinal.replaceAll("-", "/");
                    }
                    try {
                        Date FechaFinal = formato.parse(fechaFinal);
                        tNovedades.setFechafinal(FechaFinal);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setFechafinal(null);
                }
                //FECHA REPORTE
                String fechaReporte = sCadena.substring(39, 49).trim();
                if (!fechaReporte.equals("")) {
                    if (fechaReporte.indexOf("-") > 0) {
                        fechaReporte = fechaReporte.replaceAll("-", "/");
                    }
                    try {
                        Date FechaReporte = formato.parse(fechaReporte);
                        tNovedades.setFechareporte(FechaReporte);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setFechareporte(null);
                }
                //DOCUMENTO SOPORTE
                String DocumentoSoporte = sCadena.substring(49, 69).trim();
                if (!DocumentoSoporte.equals("")) {
                    try {
                        tNovedades.setDocumentosoporte(DocumentoSoporte);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }

                } else {
                    tNovedades.setDocumentosoporte(null);
                }
                //VALOR TOTAL
                String sValor = sCadena.substring(69, 88).trim();
                if (!sValor.equals("")) {
                    try {
                        if (sValor.indexOf(",") > 0) {
                            sValor = sValor.replaceAll(",", ".");
                        }
                        BigDecimal Valor = new BigDecimal(sValor);
                        tNovedades.setValortotal(Valor);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setValortotal(null);
                }
                //PERIOCIDAD
                String sPeriocidad = sCadena.substring(88, 92).trim();
                if (!sPeriocidad.equals("")) {
                    try {
                        BigInteger Periodicidad = new BigInteger(sPeriocidad);
                        tNovedades.setPeriodicidad(Periodicidad);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setPeriodicidad(null);
                }
                //TERCERO
                String tercero = sCadena.substring(92, 103).trim();
                if (!tercero.equals("")) {
                    try {
                        BigInteger Tercero = new BigInteger(tercero);
                        tNovedades.setTercero(Tercero);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setTercero(null);
                }
                //SALDO
                String saldo = sCadena.substring(103, 123).trim();
                if (!saldo.equals("")) {
                    try {
                        if (saldo.indexOf(",") > 0) {
                            saldo = sValor.replaceAll(",", ".");
                        }
                        BigDecimal Saldo = new BigDecimal(saldo);
                        tNovedades.setSaldo(Saldo);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setSaldo(null);
                }
                //UNIDAD ENTERA
                String uniE = sCadena.substring(123, 128).trim();
                if (!uniE.equals("")) {
                    try {
                        Integer UnidadEntera = Integer.parseInt(uniE);
                        tNovedades.setUnidadesparteentera(UnidadEntera);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setUnidadesparteentera(null);
                }
                //UNIDAD FRACCIONADA
                String unF = sCadena.substring(128, 133).trim();
                if (!unF.equals("")) {
                    Integer UnidadFraccion;
                    try {
                        UnidadFraccion = Integer.parseInt(unF);
                        tNovedades.setUnidadespartefraccion(UnidadFraccion);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }
                } else {
                    tNovedades.setUnidadespartefraccion(null);
                }
                //TIPO
                String sTipo = sCadena.substring(133).trim();
                if (!sTipo.equals("")) {
                    try {
                        tNovedades.setTipo(sTipo);
                    } catch (Exception e) {
                        context.update("form:errorArchivo");
                        context.execute("errorArchivo.show()");
                        break;
                    }

                } else {
                    tNovedades.setTipo(null);
                }
                //USUARIOBD
                tNovedades.setUsuariobd(UsuarioBD.getAlias());
                //ESTADO
                tNovedades.setEstado("N");
                //NOMBRE ARCHIVO
                tNovedades.setArchivo(nombreArchivo);
                //TERMINAL
                HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
                String equipo = null;
                java.net.InetAddress localMachine = null;
                if (request.getRemoteAddr().startsWith("127.0.0.1")) {
                    localMachine = java.net.InetAddress.getLocalHost();
                    equipo = localMachine.getHostAddress();
                } else {
                    equipo = request.getRemoteAddr();
                }
                localMachine = java.net.InetAddress.getByName(equipo);
                tNovedades.setTerminal(localMachine.getHostName());
                //SECUENCIA DEFAULT
                tNovedades.setSecuencia(BigInteger.valueOf(1));
                //AGREGAR NOVEDAD A LA LISTA
                listTempNovedades.add(tNovedades);
                tNovedades = null;
            }
            administrarCargueArchivos.borrarRegistrosTempNovedades(UsuarioBD.getAlias());
            insertarNovedadTempNovedades();
            listTempNovedades = null;
            listTempNovedades = administrarCargueArchivos.consultarTempNovedades(UsuarioBD.getAlias());
            subTotal = new BigDecimal(0);
            validarNovedades();
            if (listTempNovedades != null) {
                botones = true;
                cargue = false;
                /*context.update("form:tempNovedades");
                 System.out.println("Actualizo tabla");
                 context.update("form:FileUp");
                 context.update("form:nombreArchivo");
                 context.update("form:formula");
                 context.update("form:usoFormulaC");
                 context.update("form:cargar");*/
                elementosActualizar.add("form:tempNovedades");
                elementosActualizar.add("form:FileUp");
                elementosActualizar.add("form:nombreArchivo");
                elementosActualizar.add("form:formula");
                elementosActualizar.add("form:usoFormulaC");
                elementosActualizar.add("form:cargar");
                context.update(elementosActualizar);
                elementosActualizar.clear();
            }
        } catch (Exception e) {
            System.out.println("Excepcion: (leerTxt) " + e);
        }
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            columnaConcepto = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaConcepto");
            columnaConcepto.setFilterStyle("width: 60px");
            columnaDocumentoSoporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaDocumentoSoporte");
            columnaDocumentoSoporte.setFilterStyle("width: 60px");
            columnaEmpleado = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaEmpleado");
            columnaEmpleado.setFilterStyle("width: 60px");
            columnaFechaFinal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaFinal");
            columnaFechaFinal.setFilterStyle("width: 60px");
            columnaFechaInicial = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaInicial");
            columnaFechaInicial.setFilterStyle("width: 60px");
            columnaFechaReporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaReporte");
            columnaFechaReporte.setFilterStyle("width: 60px");
            columnaPeriodicidad = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaPeriodicidad");
            columnaPeriodicidad.setFilterStyle("width: 60px");
            columnaSaldo = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaSaldo");
            columnaSaldo.setFilterStyle("width: 60px");
            columnaTercero = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaTercero");
            columnaTercero.setFilterStyle("width: 60px");
            columnaUnidadEntera = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadEntera");
            columnaUnidadEntera.setFilterStyle("width: 60px");
            columnaUnidadFraccion = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadFraccion");
            columnaUnidadFraccion.setFilterStyle("width: 60px");
            columnaValorTotal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaValorTotal");
            columnaValorTotal.setFilterStyle("width: 60px");
            altoTabla = "108";
            RequestContext.getCurrentInstance().update("form:tempNovedades");
            bandera = 1;
        } else if (bandera == 1) {
            columnaConcepto = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaConcepto");
            columnaConcepto.setFilterStyle("display: none; visibility: hidden;");
            columnaDocumentoSoporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaDocumentoSoporte");
            columnaDocumentoSoporte.setFilterStyle("display: none; visibility: hidden;");
            columnaEmpleado = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaEmpleado");
            columnaEmpleado.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaFinal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaFinal");
            columnaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaInicial = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaInicial");
            columnaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaReporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaReporte");
            columnaFechaReporte.setFilterStyle("display: none; visibility: hidden;");
            columnaPeriodicidad = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaPeriodicidad");
            columnaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            columnaSaldo = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaSaldo");
            columnaSaldo.setFilterStyle("display: none; visibility: hidden;");
            columnaTercero = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaTercero");
            columnaTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaUnidadEntera = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadEntera");
            columnaUnidadEntera.setFilterStyle("display: none; visibility: hidden;");
            columnaUnidadFraccion = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadFraccion");
            columnaUnidadFraccion.setFilterStyle("display: none; visibility: hidden;");
            columnaValorTotal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaValorTotal");
            columnaValorTotal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "130";
            RequestContext.getCurrentInstance().update("form:tempNovedades");
            bandera = 0;
            filtrarListTempNovedades = null;
            tipoLista = 0;
        }
        novedadTablaSeleccionada = null;
        cualCelda = -1;
    }

    public void validarNovedades() {
        boolean validacion = false;
        List<String> erroresN;
        documentosSoporteCargados = administrarCargueArchivos.consultarDocumentosSoporteCargadosUsuario(UsuarioBD.getAlias());
        BigInteger secEmpresa = administrarCargueArchivos.consultarParametrosEmpresa(UsuarioBD.getAlias());
        for (int i = 0; i < listTempNovedades.size(); i++) {
            ErroresNovedades errorNovedad = new ErroresNovedades();
            //NUMERO DE ERRORES EN LA FILA
            int errores = 0;
            //Validad Codigo Empleado TRUE = "EXITOSO"  - FALSE = "FALLO"
            erroresN = new ArrayList<String>();
            errorNovedad.setSecNovedad(listTempNovedades.get(i).getSecuencia());
            errorNovedad.setMensajeError(erroresN);
            //PRIMERA ETAPA
            if (errores == 0) {
                //PRIMERA FASE (EXISTENCIA)
                //VALIDACION EMPLEADO
                if (listTempNovedades.get(i).getEmpleado() != null) {
                    validacion = administrarCargueArchivos.verificarEmpleadoEmpresa(listTempNovedades.get(i).getEmpleado(), secEmpresa);
                    if (validacion == false) {
                        errores++;
                        erroresN.add("El código del empleado: " + listTempNovedades.get(i).getEmpleado() + ", no existe.");
                    }
                } else {
                    errores++;
                    erroresN.add("La Empleado es necesario, campo Vacio.");
                }
                //VALIDACION CONCEPTO
                if (listTempNovedades.get(i).getConcepto() != null) {
                    validacion = administrarCargueArchivos.verificarConcepto(listTempNovedades.get(i).getConcepto());
                    if (validacion == false) {
                        errores++;
                        erroresN.add("El concepto: " + listTempNovedades.get(i).getConcepto() + ", no existe.");
                    }
                } else {
                    errores++;
                    erroresN.add("La Concepto es necesario, campo Vacio.");
                }
                //VALIDACION PERIODICIDAD
                if (listTempNovedades.get(i).getPeriodicidad() != null) {
                    validacion = administrarCargueArchivos.verificarPeriodicidad(listTempNovedades.get(i).getPeriodicidad());
                    if (validacion == false) {
                        errores++;
                        erroresN.add("La periodicidad:" + listTempNovedades.get(i).getPeriodicidad() + ", no existe.");
                    }
                } else {
                    errores++;
                    erroresN.add("La periodicidad es necesaria, campo Vacio.");
                }
                //SEGUNDA FASE (CAMPOS NO NULOS)
                //VALIDAR FECHA INICIAL
                if (listTempNovedades.get(i).getFechainicial() == null) {
                    errores++;
                    erroresN.add("La fecha inicial es necesaria, campo vacio.");
                }
                //VALIDAR FECHA REPORTE
                if (listTempNovedades.get(i).getFechareporte() == null) {
                    errores++;
                    erroresN.add("La fecha de reporte es necesaria, campo vacio.");
                }
                //VALIDAR DOCUMENTO SOPORTE
                if (listTempNovedades.get(i).getDocumentosoporte() == null) {
                    errores++;
                    erroresN.add("El documento soporte es necesario, campo vacio.");
                }
                //VALIDAR UNIDAD PARTE ENTERA
                if (listTempNovedades.get(i).getUnidadesparteentera() == null) {
                    errores++;
                    erroresN.add("La unidad parte entera es necesaria, campo vacio.");
                }
                //VALIDAR UNIDAD PARTE FRACCION
                if (listTempNovedades.get(i).getUnidadespartefraccion() == null) {
                    errores++;
                    erroresN.add("La unidad parte fracción es necesaria, campo vacio.");
                }
                //VALIDAR TIPO
                if (listTempNovedades.get(i).getTipo() == null) {
                    errores++;
                    erroresN.add("El tipo es necesario, campo vacio.");
                }
                //TERCERA FASE (CAMPOS CONDICIONADOS)
                //VALIDAD FECHA FINAL (NO PUEDE SER MENOR QUE LA INICIAL)
                if (listTempNovedades.get(i).getFechainicial() != null && listTempNovedades.get(i).getFechafinal() != null && listTempNovedades.get(i).getFechainicial().after(listTempNovedades.get(i).getFechafinal())) {
                    errores++;
                    erroresN.add("La fecha inicial no puede ser mayor que la fecha Final.");
                }
                //VALIDAR TERCERO
                if (listTempNovedades.get(i).getTercero() != null) {
                    validacion = administrarCargueArchivos.verificarTercero(listTempNovedades.get(i).getTercero());
                    if (validacion == false) {
                        errores++;
                        erroresN.add("El codigo del tercero:" + listTempNovedades.get(i).getTercero() + ", no existe.");
                    }
                }
                //VALIDAR SALDO (DEBE SER MAYOR A CERO)
                /*if (listTempNovedades.get(i).getSaldo() != null && (listTempNovedades.get(i).getSaldo().compareTo(BigDecimal.valueOf(0)) == -1 || listTempNovedades.get(i).getSaldo().compareTo(BigDecimal.valueOf(0)) == 0)) {
                 errores++;
                 erroresN.add("El Saldo deber ser mayor a cero.");
                 }*/
            }
            //SEGUNDA ETAPA
            if (errores == 0) {
                validacion = administrarCargueArchivos.verificarTipoEmpleadoActivo(listTempNovedades.get(i).getEmpleado(), secEmpresa);
                if (validacion == true) {
                    Empleados empleado = administrarCargueArchivos.consultarEmpleadoEmpresa(listTempNovedades.get(i).getEmpleado(), secEmpresa);
                    VWActualesTiposTrabajadores vwActualTipoTrabajador = administrarCargueArchivos.consultarActualTipoTrabajadorEmpleado(empleado.getSecuencia());
                    VWActualesReformasLaborales vwActualReformaLaboral = administrarCargueArchivos.consultarActualReformaLaboralEmpleado(empleado.getSecuencia());
                    VWActualesTiposContratos vwActualTiposContratos = administrarCargueArchivos.consultarActualTipoContratoEmpleado(empleado.getSecuencia());

                    Conceptos concepto = administrarCargueArchivos.verificarConceptoEmpresa(listTempNovedades.get(i).getConcepto(), empleado.getEmpresa().getSecuencia());
                    //Conceptos concepto = administrarCargueArchivos.verificarConceptoEmpresa(listTempNovedades.get(i).getConcepto(), empleado.getEmpresa());
                    if (concepto != null) {
                        if (concepto.getActivo().equalsIgnoreCase("S")) {
                            String tipoConcepto = administrarCargueArchivos.determinarTipoConcepto(concepto.getSecuencia());

                            if (tipoConcepto.equalsIgnoreCase("MANUAL") || tipoConcepto.equalsIgnoreCase("SEMI-AUTOMATICO")) {
                                if (listTempNovedades.get(i).getValortotal() != null) {
                                    subTotal = subTotal.add(listTempNovedades.get(i).getValortotal());
                                }
                                validacion = administrarCargueArchivos.verificarZonaT(concepto.getSecuencia(), vwActualReformaLaboral.getReformaLaboral().getSecuencia(), vwActualTiposContratos.getTipoContrato().getSecuencia(), vwActualTipoTrabajador.getTipoTrabajador().getSecuencia());
                                if (validacion == false) {
                                    errores++;
                                    erroresN.add("La Zona T del concepto no coicide con la del empleado (" + empleado.getCodigoempleado() + ").");
                                }
                            }
                            if (tipoConcepto.equalsIgnoreCase("MANUAL")) {
                                if (listTempNovedades.get(i).getValortotal() == null) {
                                    errores++;
                                    erroresN.add("El concepto es manual, por ende es necesario un valor, campo vacio.");
                                }
                                if (listTempNovedades.get(i).getValortotal() != null && listTempNovedades.get(i).getValortotal().compareTo(BigDecimal.valueOf(0)) == 0) {
                                    errores++;
                                    erroresN.add("El concepto es manual, por lo tanto, el valor total debe ser mayor o menor a cero.");
                                }
                                if (listTempNovedades.get(i).getUnidadesparteentera() == null || listTempNovedades.get(i).getUnidadesparteentera() != 0) {
                                    errores++;
                                    erroresN.add("El concepto es manual, por lo tanto la unidad parte entera debe ser cero.");
                                }
                                if (listTempNovedades.get(i).getUnidadespartefraccion() == null || listTempNovedades.get(i).getUnidadespartefraccion() != 0) {
                                    errores++;
                                    erroresN.add("El concepto es manual, por lo tanto la unidad parte fraccion debe ser cero.");
                                }
                                if (listTempNovedades.get(i).getSaldo() != null && (listTempNovedades.get(i).getSaldo().compareTo(BigDecimal.valueOf(0)) == -1 || listTempNovedades.get(i).getSaldo().compareTo(BigDecimal.valueOf(0)) == 0)) {
                                    errores++;
                                    erroresN.add("El Saldo deber ser mayor a cero.");
                                }
                            } else if (tipoConcepto.equalsIgnoreCase("SEMI-AUTOMATICO")) {

                                if (listTempNovedades.get(i).getUnidadesparteentera() == 0 && listTempNovedades.get(i).getUnidadespartefraccion() == 0) {
                                    errores++;
                                    erroresN.add("El concepto es semi-automatico, por lo tanto solo una de las 2 unidades (Unidad Fraccion - Unidad Entera), puede ser cero.");
                                } else {
                                    if (listTempNovedades.get(i).getUnidadesparteentera() < 0) {
                                        errores++;
                                        erroresN.add("El concepto es semi-automatico, por lo tanto la unidad parte entera debe ser un valor positivo.");
                                    }
                                    if (listTempNovedades.get(i).getUnidadespartefraccion() < 0) {
                                        errores++;
                                        erroresN.add("El concepto es semi-automatico, por lo tanto la unidad parte fracción debe ser un valor positivo.");
                                    }
                                }
                                if (listTempNovedades.get(i).getSaldo() != null) {
                                    errores++;
                                    erroresN.add("El concepto es semi-automatico, por lo tanto el saldo debe estar vacio.");
                                }
                                if (listTempNovedades.get(i).getValortotal() != null) {
                                    if (listTempNovedades.get(i).getValortotal().compareTo(BigDecimal.valueOf(0)) != 0) {
                                        errores++;
                                        erroresN.add("El concepto es semi-automatico, por lo tanto el valor total debe ser cero.");
                                    }
                                } else {
                                    errores++;
                                    erroresN.add("El concepto es semi-automatico, por lo tanto valor total no puede estar vacio y debe ser cero (0).");
                                }
                                if (usarFormulaConcepto.equals("N")) {
                                    validacion = administrarCargueArchivos.verificarFormulaCargueConcepto(concepto.getSecuencia(), formulaUsada.getSecuencia());
                                    if (validacion == false) {
                                        errores++;
                                        erroresN.add("La formula seleccionada para el cargue no coicide con la del concepto.");
                                    }
                                }
                                validacion = administrarCargueArchivos.verificarNecesidadTercero(concepto.getSecuencia());
                                if (validacion == true && listTempNovedades.get(i).getTercero() == null) {
                                    errores++;
                                    erroresN.add("El concepto pertenece al grupo 1, por lo tanto es necesario un tercero.");
                                }
                                if (listTempNovedades.get(i).getTercero() != null) {
                                    validacion = administrarCargueArchivos.verificarTerceroEmpresa(listTempNovedades.get(i).getTercero(), empleado.getEmpresa().getSecuencia());
                                    //validacion = administrarCargueArchivos.verificarTerceroEmpresa(listTempNovedades.get(i).getTercero(), empleado.getEmpresa());
                                    if (validacion == false) {
                                        errores++;
                                        erroresN.add("El tercero con nit: " + listTempNovedades.get(i).getTercero() + " no existe para la empresa a la cual esta vinculado el empleado.");
                                    }
                                }
                                if (listTempNovedades.get(i).getFechafinal() == null) {
                                    errores++;
                                    erroresN.add("El concepto es semi-automatico, por lo tanto requiere una fecha final.");
                                }
                                if (usarFormulaConcepto.equals("N")) {
                                    errores++;
                                    erroresN.add("El concepto es semi-automatico, por lo tanto debe utilizar la formula del concepto.");
                                }
                            } else {
                                errores++;
                                erroresN.add("No se puede usar el Concepto:" + listTempNovedades.get(i).getConcepto() + ", porque es automatico.");
                            }
                        } else {
                            errores++;
                            erroresN.add("El Concepto:" + listTempNovedades.get(i).getConcepto() + ", no esta activo.");
                        }
                    } else {
                        errores++;
                        erroresN.add("El codigo del concepto:" + listTempNovedades.get(i).getConcepto() + ", no existe en la empresa a la cual pertenece el empleado.");
                    }
                } else {
                    errores++;
                    erroresN.add("El Empleado: " + listTempNovedades.get(i).getEmpleado() + ", debe ser Activo.");
                }
            } else {
                //MARCAR EL REGISTRO EN LA BASE DE DATOS PARA ASBER QUE TIENE ERRORES
                errorNovedad.setNumeroErrores(errores);
                errorNovedad.setMensajeError(erroresN);
                listTempNovedades.get(i).setEstadovalidacion("I");
                administrarCargueArchivos.modificarTempNovedades(listTempNovedades.get(i));
            }
            //TERCERA ETAPA
            RequestContext context = RequestContext.getCurrentInstance();
            if (errores == 0) {
                if (listTempNovedades.get(i).getDocumentosoporte().length() > 20) {
                    errores++;
                    erroresN.add("El documento soporte debe ser maximo de 20 caracteres.");
                } else {
                    int duplicado = 0;
                    for (int j = 0; j < documentosSoporteCargados.size(); j++) {
                        if (listTempNovedades.get(i).getDocumentosoporte().equalsIgnoreCase(documentosSoporteCargados.get(j))) {
                            duplicado++;
                        }
                    }
                    if (duplicado > 0) {
                        errores++;
                        erroresN.add("El documento soporte (" + listTempNovedades.get(i).getDocumentosoporte() + ") ya existe, cambie el nombre del mismo.");

                    }
                }
                if (listTempNovedades.get(i).getTipo().equalsIgnoreCase("OCASIONAL") || listTempNovedades.get(i).getTipo().equalsIgnoreCase("FIJA") || listTempNovedades.get(i).getTipo().equalsIgnoreCase("PAGO POR FUERA")) {
                } else {
                    errores++;
                    erroresN.add("El tipo de novedad debe ser Ocasional, Fija o Pago por Fuera.");

                }
                context.update("form:subtotal");
            } else {
                errorNovedad.setNumeroErrores(errores);
                errorNovedad.setMensajeError(erroresN);
                listTempNovedades.get(i).setEstadovalidacion("I");
                administrarCargueArchivos.modificarTempNovedades(listTempNovedades.get(i));
                context.update("form:subtotal");
            }
            //FINAL
            if (errores == 0) {
                errorNovedad.setNumeroErrores(errores);
                errorNovedad.setMensajeError(erroresN);
                listTempNovedades.get(i).setEstadovalidacion("C");
                administrarCargueArchivos.modificarTempNovedades(listTempNovedades.get(i));
                context.update("form:subtotal");
            } else {
                errorNovedad.setNumeroErrores(errores);
                errorNovedad.setMensajeError(erroresN);
                listTempNovedades.get(i).setEstadovalidacion("I");
                administrarCargueArchivos.modificarTempNovedades(listTempNovedades.get(i));
                context.update("form:subtotal");
            }
            listErrores.add(errorNovedad);
            errorNovedad = null;
            erroresN = null;
        }
        /*for (int i = 0; i < listErrores.size(); i++) {
         |out.println("\nSecuencia: " + listErrores.get(i).getSecNovedad() + "\nError: " + listErrores.get(i).getMensajeError());
         }*/
        //administrarCargueArchivos.revisarConcepto(12);
    }

    public void revisarNovedad(BigInteger secnovedad) {
        BigInteger secuencia;
        for (int i = 0; i < listErrores.size(); i++) {
            secuencia = listErrores.get(i).getSecNovedad();
            if (secuencia.compareTo(secnovedad) == 0) {
                erroresNovedad = listErrores.get(i);
                i = listErrores.size();
            }
        }
        if (erroresNovedad != null) {
            if (erroresNovedad.getNumeroErrores() != 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:erroresNovedad");
                context.execute("erroresNovedad.show()");
            }
        }
    }

    public void insertarNovedadTempNovedades() {
        if (!listTempNovedades.isEmpty()) {
            administrarCargueArchivos.crearTempNovedades(listTempNovedades);
        }
    }

    public void formulaParaUsar() {
        formulaUsada = formulaSelecionada;
        filtradoFormulas = null;
        formulaSelecionada = new Formulas();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:formula");
        
        context.reset("formDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("formulasDialogo.hide()");
        context.update("formDialogos:formulasDialogo");
        context.update("formDialogos:lovFormulas");
        context.update("formDialogos:aceptarF");
    }

    public void cancelarSeleccionFormula() {
        filtradoFormulas = null;
        formulaSelecionada = new Formulas();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("formulasDialogo.hide()");
    }

    //AUTOCOMPLETAR FORMULAS
    public void valorAnteriorFormula() {
        nombreCorto = formulaUsada.getNombrecorto();
    }

    public void botonListaValores() {
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistroFormulas(listaFormulas.size());
        context.update("formDialogos:formulasDialogo");
        context.execute("formulasDialogo.show()");
    }

    public void autocompletarFormula(String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        formulaUsada.setNombrecorto(nombreCorto);

        for (int i = 0; i < listaFormulas.size(); i++) {
            if (listaFormulas.get(i).getNombrecorto().startsWith(valorConfirmar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }

        if (coincidencias == 1) {
            formulaUsada = listaFormulas.get(indiceUnicoElemento);
            listaFormulas = null;
            getListaFormulas();
        } else {
            context.update("formDialogos:formulasDialogo");
            context.execute("formulasDialogo.show()");
        }
        context.update("form:formula");
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            columnaConcepto = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaConcepto");
            columnaConcepto.setFilterStyle("display: none; visibility: hidden;");
            columnaDocumentoSoporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaDocumentoSoporte");
            columnaDocumentoSoporte.setFilterStyle("display: none; visibility: hidden;");
            columnaEmpleado = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaEmpleado");
            columnaEmpleado.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaFinal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaFinal");
            columnaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaInicial = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaInicial");
            columnaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaReporte = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaFechaReporte");
            columnaFechaReporte.setFilterStyle("display: none; visibility: hidden;");
            columnaPeriodicidad = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaPeriodicidad");
            columnaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            columnaSaldo = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaSaldo");
            columnaSaldo.setFilterStyle("display: none; visibility: hidden;");
            columnaTercero = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaTercero");
            columnaTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaUnidadEntera = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadEntera");
            columnaUnidadEntera.setFilterStyle("display: none; visibility: hidden;");
            columnaUnidadFraccion = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaUnidadFraccion");
            columnaUnidadFraccion.setFilterStyle("display: none; visibility: hidden;");
            columnaValorTotal = (Column) c.getViewRoot().findComponent("form:tempNovedades:columnaValorTotal");
            columnaValorTotal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "130";
            RequestContext.getCurrentInstance().update("form:tempNovedades");
            bandera = 0;
            filtrarListTempNovedades = null;
            tipoLista = 0;
        }
        nombreArchivoPlano = null;
        formulaUsada = null;
        getFormulaUsada();
        usarFormulaConcepto = "S";
        listTempNovedades = null;
        getListTempNovedades();
        contarRegistros();
        novedadTablaSeleccionada = null;
        cualCelda = -1;
        subTotal = new BigDecimal("0");
        RequestContext context = RequestContext.getCurrentInstance();
        //context.update("form:infoRegistro");
        context.update("form:nombreArchivo");
        context.update("form:formula");
        context.update("form:usoFormulaC");
        context.update("form:subtotal");
    }

    //AUTOCOMPLETAR DOCUMENTO SOPORTE
    public void autocompletarDocumentoSoporte() {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        documentosSoporteCargados = null;
        getDocumentosSoporteCargados();
        hs.addAll(documentosSoporteCargados);
        documentosSoporteCargados.clear();
        documentosSoporteCargados.addAll(hs);
        for (int i = 0; i < documentosSoporteCargados.size(); i++) {
            if (documentosSoporteCargados.get(i).startsWith(documentoSoporteReversar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }

        if (coincidencias == 1) {
            documentoSoporteReversar = documentosSoporteCargados.get(indiceUnicoElemento);
            documentosSoporteCargados = null;
            getDocumentosSoporteCargados();
        } else {
            documentoSoporteReversar = null;
            context.update("formDialogos:documentoSoporteDialogo");
            context.execute("documentoSoporteDialogo.show()");
        }
        context.update("form:documentoR");
    }

    //LOV DOCUMENTOS SOPORTE
    public void seleccionarDocumentoSoporte() {
        filtradoDocumentosSoporteCargados = null;
        documentosSoporteCargados = null;
        aceptar = true;
        documentoSoporteReversar = seleccionDocumentosSoporteCargado;
        seleccionDocumentosSoporteCargado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:documentoR");
        context.reset("formDialogos:lovDocumentoSoporte:globalFilter");
        context.execute("lovDocumentoSoporte.clearFilters()");
        context.execute("documentoSoporteDialogo.hide()");
        //context.update("formDialogos:lovDocumentoSoporte");
    }

    public void cancelarSeleccionDocumentoSoporte() {
        filtradoDocumentosSoporteCargados = null;
        documentosSoporteCargados = null;
        seleccionDocumentosSoporteCargado = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formDialogos:lovDocumentoSoporte:globalFilter");
        context.execute("lovDocumentoSoporte.clearFilters()");
        context.execute("documentoSoporteDialogo.hide()");
    }

    public void llamarDialogoDocumentoSoporte() {
        RequestContext context = RequestContext.getCurrentInstance();
        documentosSoporteCargados = null;
        context.update("formDialogos:lovDocumentoSoporte");
        context.execute("documentoSoporteDialogo.show()");
    }
    //CARGUE NOVEDADES

    public void cargarNovedades() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listTempNovedades.isEmpty() || listTempNovedades != null) {
            int pasa = 0;
            for (int i = 0; i < listErrores.size(); i++) {
                if (listErrores.get(i).getNumeroErrores() != 0) {
                    pasa++;
                }
            }
            if (pasa == 0) {
                administrarCargueArchivos.cargarTempNovedades(listTempNovedades.get(0).getFechareporte(), formulaUsada.getNombrecorto(), usarFormulaConcepto);
                int registrosNAntes = listTempNovedades.size();
                listTempNovedades = administrarCargueArchivos.consultarTempNovedades(UsuarioBD.getAlias());
                int registrosNDespues = listTempNovedades.size();
                diferenciaRegistrosN = registrosNAntes - registrosNDespues;
                context.update("form:tempNovedades");
                if (diferenciaRegistrosN == registrosNAntes) {
                    context.update("form:novedadesCargadas");
                    context.execute("novedadesCargadas.show()");
                }
                subTotal = new BigDecimal(0);
                context.update("form:subtotal");
                listErrores.clear();
                erroresNovedad = null;
                botones = false;
                cargue = true;
                nombreArchivoPlano = null;
                documentosSoportes = null;
                context.update("form:pickListDocumentosSoporte");
                context.update("form:FileUp");
                context.update("form:nombreArchivo");
                context.update("form:formula");
                context.update("form:usoFormulaC");
                context.update("form:cargar");
            }
        }
    }

    public void borrarRegistrosNoCargados() {
        administrarCargueArchivos.borrarRegistrosTempNovedades(UsuarioBD.getAlias());
        listTempNovedades = null;
        listTempNovedades = administrarCargueArchivos.consultarTempNovedades(UsuarioBD.getAlias());
        nombreArchivoPlano = null;
        subTotal = new BigDecimal(0);
        botones = false;
        cargue = true;
        modificarInfoRegistro(listTempNovedades.size());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:subtotal");
        context.update("form:FileUp");
        context.update("form:formula");
        context.update("form:usoFormulaC");
        context.update("form:cargar");
        context.update("form:tempNovedades");
        context.update("form:nombreArchivo");
        listErrores.clear();
        erroresNovedad = null;
    }

    public void reversar() {
        RequestContext context = RequestContext.getCurrentInstance();
        resultado = administrarCargueArchivos.reversarNovedades(UsuarioBD, documentoSoporteReversar);
        documentoSoporteReversar = null;
        context.update("form:documentoR");
        context.execute("reversarDialogo.hide()");
        context.execute("confirmarReversar.hide()");
        if (resultado > 0) {
            context.update("form:reversarExito");
            context.execute("reversarExito.show()");
        } else {
            context.update("form:reversarFallo");
            context.execute("reversarFallo.show()");
        }
    }

    public void cancelarReversar() {
        RequestContext context = RequestContext.getCurrentInstance();
        documentoSoporteReversar = null;
        context.update("form:documentoR");
        context.execute("reversarDialogo.hide()");
    }

    public void confirmarReversar() {
        RequestContext context = RequestContext.getCurrentInstance();
        documentosSoporteCargados = administrarCargueArchivos.consultarDocumentosSoporteCargadosUsuario(UsuarioBD.getAlias());
        hs.addAll(documentosSoporteCargados);
        documentosSoporteCargados.clear();
        documentosSoporteCargados.addAll(hs);
        hs.clear();
        int existeDocumento = 0;
        for (int i = 0; i < documentosSoporteCargados.size(); i++) {
            if (documentosSoporteCargados.get(i).equals(documentoSoporteReversar)) {
                existeDocumento++;
            }
        }
        if (existeDocumento == 1) {
            context.update("form:confirmarReversar");
            context.execute("confirmarReversar.show()");
        }
    }

    //
    public void confirmarBorrarTodo() {
        if (!documentosSoportes.getTarget().isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            resultadoProceso = administrarCargueArchivos.BorrarTodo(UsuarioBD, documentosSoportes.getTarget());
            documentosSoportes = null;
            context.execute("borrarTodoDialogo.hide()");
            context.update("form:pickListDocumentosSoporte");
            if (resultadoProceso.getDocumentosNoBorrados() == null) {
                context.update("form:borrarTodoExito");
                context.execute("borrarTodoExito.show()");
            } else {
                context.update("form:erroresBorrarTodo");
                context.execute("erroresBorrarTodo.show()");
            }
        }
    }

    public void cancelarBorrarTodo() {
        RequestContext context = RequestContext.getCurrentInstance();
        documentosSoportes = null;
        context.execute("borrarTodoDialogo.hide()");
        context.update("form:pickListDocumentosSoporte");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void prueba() {
        //administrarCargueArchivos.validarConceptoEmpresa(BigInteger.valueOf(20002), BigInteger.valueOf(19931));
        //administrarCargueArchivos.validarTipoEmpleadoActivo(BigInteger.valueOf(1022337240));
    }

    public void exportPDF() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:exportarTempNovedades");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Novedades_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:exportarTempNovedades");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Novedades_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

       public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
           modificarInfoRegistro(filtrarListTempNovedades.size());
    }
    
    public void modificarInfoRegistro(int valor){
        infoRegistro = String.valueOf(valor);
    }
    
    public void modificarInfoRegistroFormulas(int valor){
        infoRegistroFormula = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("formDialogos:infoRegistroFormula");
        
    }
    
    public void modificarInfoRegistroDocumentos(int valor){
        infoRegistroDocumento = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("formDialogos:infoRegistroDocumento");
    }
    
    public void contarRegistros(){
        if(listTempNovedades != null){
            modificarInfoRegistro(listTempNovedades.size());
        } else{
            modificarInfoRegistro(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistro");
    }
    //GETTER AND SETTER
    public List<TempNovedades> getListTempNovedades() {
        if (UsuarioBD == null) {
            UsuarioBD = administrarCargueArchivos.actualUsuario();
        }
        if (UsuarioBD.getAlias() != null) {
            listTempNovedades = administrarCargueArchivos.consultarTempNovedades(UsuarioBD.getAlias());
        }

        return listTempNovedades;
    }

    public void setListTempNovedades(List<TempNovedades> listTempNovedades) {
        this.listTempNovedades = listTempNovedades;
    }

    public ErroresNovedades getErroresNovedad() {
        return erroresNovedad;
    }

    public List<Formulas> getListaFormulas() {
        listaFormulas = administrarCargueArchivos.consultarFormulasCargue();
        return listaFormulas;
    }

    public void setListaFormulas(List<Formulas> listaFormulas) {
        this.listaFormulas = listaFormulas;
    }

    public List<Formulas> getFiltradoFormulas() {
        return filtradoFormulas;
    }

    public void setFiltradoFormulas(List<Formulas> filtradoFormulas) {
        this.filtradoFormulas = filtradoFormulas;
    }

    public Formulas getFormulaSelecionada() {
        return formulaSelecionada;
    }

    public void setFormulaSelecionada(Formulas formulaSelecionada) {
        this.formulaSelecionada = formulaSelecionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getUsarFormulaConcepto() {
        return usarFormulaConcepto;
    }

    public void setUsarFormulaConcepto(String usarFormulaConcepto) {
        this.usarFormulaConcepto = usarFormulaConcepto;
    }

    public Formulas getFormulaUsada() {
        if (formulaUsada == null) {
            formulaUsada = administrarCargueArchivos.consultarFormulaCargueInicial();
        }
        return formulaUsada;
    }

    public void setFormulaUsada(Formulas formulaUsada) {
        this.formulaUsada = formulaUsada;
    }

    public String getNombreArchivoPlano() {
        return nombreArchivoPlano;
    }

    public void setNombreArchivoPlano(String nombreArchivoPlano) {
        this.nombreArchivoPlano = nombreArchivoPlano;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public boolean isBotones() {
        return botones;
    }

    public boolean isCargue() {
        return cargue;
    }

    public String getDocumentoSoporteReversar() {
        return documentoSoporteReversar;
    }

    public void setDocumentoSoporteReversar(String documentoSoporteReversar) {
        this.documentoSoporteReversar = documentoSoporteReversar;
    }

    public List<String> getDocumentosSoporteCargados() {
        //if (documentosSoporteCargados == null) {
        documentosSoporteCargados = administrarCargueArchivos.consultarDocumentosSoporteCargadosUsuario(UsuarioBD.getAlias());
        hs.addAll(documentosSoporteCargados);
        documentosSoporteCargados.clear();
        documentosSoporteCargados.addAll(hs);
        hs.clear();
        //}
        return documentosSoporteCargados;
    }

    public void setDocumentosSoporteCargados(List<String> documentosSoporteCargados) {
        this.documentosSoporteCargados = documentosSoporteCargados;
    }

    public List<String> getFiltradoDocumentosSoporteCargados() {
        return filtradoDocumentosSoporteCargados;
    }

    public void setFiltradoDocumentosSoporteCargados(List<String> filtradoDocumentosSoporteCargados) {
        this.filtradoDocumentosSoporteCargados = filtradoDocumentosSoporteCargados;
    }

    public String getSeleccionDocumentosSoporteCargado() {
        return seleccionDocumentosSoporteCargado;
    }

    public void setSeleccionDocumentosSoporteCargado(String seleccionDocumentosSoporteCargado) {
        this.seleccionDocumentosSoporteCargado = seleccionDocumentosSoporteCargado;
    }

    public int getDiferenciaRegistrosN() {
        return diferenciaRegistrosN;
    }

    public int getResultado() {
        return resultado;
    }

    public DualListModel<String> getDocumentosSoportes() {
        documentosSoporteCargados = null;
        getDocumentosSoporteCargados();
        documentosSoportes = new DualListModel<String>(documentosSoporteCargados, documentosEscogidos);
        return documentosSoportes;
    }

    public void setDocumentosSoportes(DualListModel<String> documentosSoportes) {
        this.documentosSoportes = documentosSoportes;
    }

    public ResultadoBorrarTodoNovedades getResultadoProceso() {
        return resultadoProceso;
    }

    public void setResultadoProceso(ResultadoBorrarTodoNovedades resultadoProceso) {
        this.resultadoProceso = resultadoProceso;
    }

    public void host() throws UnknownHostException {
        // Get the request-object.  
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());

// Get the header attributes. Use them to retrieve the actual  
// values.  
        System.out.println(request.getHeaderNames());

// Get the IP-address of the client.  
        System.out.println(request.getRemoteAddr());

// Get the hostname of the client.  
        System.out.println(request.getRemotePort());

        String equipo = null;
        java.net.InetAddress localMachine = null;
        if (request.getRemoteAddr().startsWith("127.0.0.1")) {
            localMachine = java.net.InetAddress.getLocalHost();
            equipo = localMachine.getHostAddress();
        } else {
            equipo = request.getRemoteAddr();
        }
        localMachine = java.net.InetAddress.getByName(equipo);
        System.out.println(localMachine.getHostName());
    }

    public List<TempNovedades> getFiltrarListTempNovedades() {
        return filtrarListTempNovedades;
    }

    public void setFiltrarListTempNovedades(List<TempNovedades> filtrarListTempNovedades) {
        this.filtrarListTempNovedades = filtrarListTempNovedades;
    }

    public TempNovedades getNovedadTablaSeleccionada() {
        //getListTempNovedades();
        return novedadTablaSeleccionada;
    }

    public void setNovedadTablaSeleccionada(TempNovedades novedadTablaSeleccionada) {
        this.novedadTablaSeleccionada = novedadTablaSeleccionada;
    }

    public String getInfoRegistroFormula() {
      //  getListaFormulas();
        return infoRegistroFormula;
    }

    public void setInfoRegistroFormula(String infoRegistroFormula) {
        this.infoRegistroFormula = infoRegistroFormula;
    }

    public String getInfoRegistroDocumento() {
       // getDocumentosSoporteCargados();
        return infoRegistroDocumento;
    }

    public void setInfoRegistroDocumento(String infoRegistroDocumento) {
        this.infoRegistroDocumento = infoRegistroDocumento;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public TempNovedades getEditarNovedad() {
        return editarNovedad;
    }

    public void setEditarNovedad(TempNovedades editarNovedad) {
        this.editarNovedad = editarNovedad;
    }

}
