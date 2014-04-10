package Controlador;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import java.util.*;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("columna0", "columna1", "columna2", "columna3", "columna4", "columna5", "columna6", "columna7", "columna8", "columna9");
    //Nombres de los valores en la clase que tomaran lo valores correspondientes de las columnas que se desean agregar

    private String columnTemplate;
    //Valores que seran mostrados en los header de cada columna

    private Map<String, String> valoresColumnas;

    //Cargue de los valores static de los arreglos color y manufactura
    private List<ColumnasBusquedaAvanzada> filteredCars;
    //Lista de filtrado

    private List<ColumnasBusquedaAvanzada> carsSmall;
    //Lista de carros que sera cargada por la tabla

    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    //Lista de las columnas que seran agregadas a la tabla

    private String nuevaColumna;

    public ControlPruebasUnitarias() {
        nuevaColumna = "";
        columnTemplate = "Codigo Primer_Apellido Segundo_Apellido Nombre";
        valoresColumnas = new HashMap<String, String>();
        carsSmall = new ArrayList<ColumnasBusquedaAvanzada>();
        createDynamicColumns();
    }

    public List<ColumnasBusquedaAvanzada> getCarsSmall() {
        return carsSmall;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public List<ColumnasBusquedaAvanzada> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<ColumnasBusquedaAvanzada> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public String getNuevaColumna() {
        return nuevaColumna;
    }

    public void setNuevaColumna(String nuevaColumna) {
        this.nuevaColumna = nuevaColumna;
    }

    //Clase de columnas que sera cargadas en la tabla
    static public class ColumnModel implements Serializable {

        private String header;
        //Cabecera de la columna
        private String property;
        //Valor que sera mostrado en cada columna

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        table.setValueExpression("sortBy", null);
        //update columns
        if (!columnTemplate.isEmpty()) {
            createDynamicColumns();
            cargarTabla();
        }
    }

    public void createDynamicColumns() {
        String[] valoresColumnasDeseadas = columnTemplate.split(" ");
        columns.clear();
        valoresColumnas.clear();
        for (int i = 0; i < valoresColumnasDeseadas.length; i++) {
            String nameColumna = "columna" + String.valueOf(i);
            valoresColumnas.put(nameColumna, valoresColumnasDeseadas[i]);
        }
        int numeroColumnas = valoresColumnas.size();
        for (int i = 0; i < numeroColumnas; i++) {
            columns.add(i, new ColumnModel("", ""));
        }
        for (Map.Entry<String, String> entry : valoresColumnas.entrySet()) {
            String numero = entry.getKey().charAt(entry.getKey().length() - 1) + "";
            int numeroCol = Integer.parseInt(numero);
            if (VALID_COLUMN_KEYS.contains(entry.getKey())) {
                columns.set(numeroCol, new ColumnModel(entry.getValue().toUpperCase(), entry.getKey().toString()));
            }
        }
    }

    public void cargarTabla() {
        carsSmall = new ArrayList<ColumnasBusquedaAvanzada>();
        for (int i = 0; i < 10; i++) {
            carsSmall.add(new ColumnasBusquedaAvanzada("", "", "", "", "", "", "", "", "", ""));
        }
        int tam = columns.size();
        for (int i = 0; i < tam; i++) {
            if (columns.get(i).getProperty().equalsIgnoreCase("columna0")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna0(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna1")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna1(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna2")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna2(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna3")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna3(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna4")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna4(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna5")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna5(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna6")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna6(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna7")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna7(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna8")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna8(aux);
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("columna9")) {
                String aux = columns.get(i).getHeader();
                for (int j = 0; j < 10; j++) {
                    carsSmall.get(j).setColumna9(aux);
                }
            }
            //list.add(new ColumnasBusquedaAvanzada(getRandomModel(), String.valueOf(getRandomYear()), getRandomManufacturer(), getRandomColor()));
        }
    }

    public void restaurar() {
        columnTemplate = "Codigo Primer_Apellido Segundo_Apellido Nombre";
        carsSmall = new ArrayList<ColumnasBusquedaAvanzada>();
        createDynamicColumns();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:cars");
        context.update("form:template");
    }

    public void dispararDialogo() {
        String[] valoresColumnasDeseadas = columnTemplate.split(" ");
        if (valoresColumnasDeseadas.length < 10) {
            RequestContext context = RequestContext.getCurrentInstance();
            nuevaColumna = "";
            context.update("form:nuevaColumna");
            context.execute("nuevaColumna.show()");
        } else {
            System.out.println("No se peuden agregar mas columnas");
        }
    }

    public void agregarColumna() {
        columnTemplate = columnTemplate + nuevaColumna + " ";
        nuevaColumna = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:nuevaColumna");
        context.update("form:template");
    }
}
