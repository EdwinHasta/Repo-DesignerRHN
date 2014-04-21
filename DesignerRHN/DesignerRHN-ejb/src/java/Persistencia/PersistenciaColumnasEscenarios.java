/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import Entidades.ColumnasEscenarios;
import Entidades.Empleados;
import InterfacePersistencia.PersistenciaColumnasEscenariosInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class PersistenciaColumnasEscenarios implements PersistenciaColumnasEscenariosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<ColumnasEscenarios> buscarColumnasEscenarios() {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ColumnasEscenarios cc WHERE ESCENARIO = (select SECUENCIA from escenarios where QVWNOMBRE= 'QVWEMPLEADOSCORTE') ORDER BY cc.nombrecolumna ASC", ColumnasEscenarios.class);
            List<ColumnasEscenarios> competenciascargos = query.getResultList();
            return competenciascargos;
        } catch (Exception e) {
            System.out.println("Error buscarColumnasEscenarios PersistenciaColumnasEscenarios : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ColumnasBusquedaAvanzada> buscarQVWEmpleadosCorteCodigoEmpleado(List<Empleados> listaEmpleadosResultados, List<String> campos) {
        try {
            System.out.println("Entrto persistencia");
            List<ColumnasBusquedaAvanzada> registro = new ArrayList<ColumnasBusquedaAvanzada>();
            for (int j = 0; j < listaEmpleadosResultados.size(); j++) {
                System.out.println("listaEmpleadosResultados : "+listaEmpleadosResultados.size());
                ColumnasBusquedaAvanzada obj = new ColumnasBusquedaAvanzada();
                registro.add(obj);
                for (int i = 0; i < campos.size(); i++) {
                    String campo = campos.get(i);
                    if (campo.contains("FECHA")) {
                        String auxiliar = "TO_CHAR(" + campo + ",'DD/MM/YYYY')";
                        campo = auxiliar;
                    } else {
                        String auxiliar = "TO_CHAR(" + campo + ")";
                        campo = auxiliar;
                    }
                    String q = "SELECT " + campo + " FROM QVWEmpleadosCorte q WHERE q.codigoempleado=" + listaEmpleadosResultados.get(j).getCodigoempleado();
                    Query query = em.createNativeQuery(q);
                    String valor = (String) query.getSingleResult();
                    System.out.println("Valor : "+valor);
                    if (i == 0) {
                        registro.get(j).setColumna0(valor);
                    }
                    if (i == 1) {
                        registro.get(j).setColumna1(valor);
                    }
                    if (i == 2) {
                        registro.get(j).setColumna2(valor);
                    }
                    if (i == 3) {
                        registro.get(j).setColumna3(valor);
                    }
                    if (i == 4) {
                        registro.get(j).setColumna4(valor);
                    }
                    if (i == 5) {
                        registro.get(j).setColumna5(valor);
                    }
                    if (i == 6) {
                        registro.get(j).setColumna6(valor);
                    }
                    if (i == 7) {
                        registro.get(j).setColumna7(valor);
                    }
                    if (i == 8) {
                        registro.get(j).setColumna8(valor);
                    }
                    if (i == 9) {
                        registro.get(j).setColumna9(valor);
                    }
                }
            }
            return registro;

        } catch (Exception e) {
            System.out.println("Error buscarQVWEmpleadosCorteCodigoEmpleado PersistenciaQVWEmpleadosCorte : " + e.toString());
            return null;
        }
    }

}
