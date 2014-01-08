/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarClasesAccidentesInterface;
import Entidades.ClasesAccidentes;
import InterfacePersistencia.PersistenciaClasesAccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarClasesAccidentes implements AdministrarClasesAccidentesInterface {

    @EJB
    PersistenciaClasesAccidentesInterface persistenciaClasesAccidentes;
    private ClasesAccidentes clasesAccidentesSeleccionada;
    private ClasesAccidentes clasesAccidentes;
    private List<ClasesAccidentes> listClasesAccidentes;

    public void modificarClasesAccidentes(List<ClasesAccidentes> listClasesAccidentesModificada) {
        for (int i = 0; i < listClasesAccidentesModificada.size(); i++) {
            System.out.println("Administrar Modificando...");
            clasesAccidentesSeleccionada = listClasesAccidentesModificada.get(i);
            persistenciaClasesAccidentes.editar(clasesAccidentesSeleccionada);
        }
    }

    public void borrarClasesAccidentes(ClasesAccidentes tiposAccidentes) {
        persistenciaClasesAccidentes.borrar(tiposAccidentes);
    }

    public void crearClasesAccidentes(ClasesAccidentes TiposAccidentes) {
        persistenciaClasesAccidentes.crear(TiposAccidentes);
    }

    public List<ClasesAccidentes> mostrarClasesAccidentes() {
        listClasesAccidentes = persistenciaClasesAccidentes.buscarClasesAccidentes();
        return listClasesAccidentes;
    }

    public ClasesAccidentes mostrarClaseAccidente(BigInteger secClasesAccidentes) {
        clasesAccidentes = persistenciaClasesAccidentes.buscarClaseAccidente(secClasesAccidentes);
        return clasesAccidentes;
    }

    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos) {
        BigInteger verificarSoAccidtenesMedicos = null;
        try {
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            verificarSoAccidtenesMedicos = persistenciaClasesAccidentes.contadorSoAccidentesMedicos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarClasesAccidentes verificarSoAccidtenesMedicos ERROR :" + e);
        } finally {
            return verificarSoAccidtenesMedicos;
        }
    }
}
