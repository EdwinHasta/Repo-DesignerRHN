/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarPaisesInterface;
import Entidades.Paises;
import InterfacePersistencia.PersistenciaPaisesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarPaises implements AdministrarPaisesInterface {

    @EJB
    PersistenciaPaisesInterface persistenciaPaises;

    @Override
    public void modificarPaises(List<Paises> listaPaises) {
        for (int i = 0; i < listaPaises.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaPaises.editar(listaPaises.get(i));
        }
    }

    @Override
    public void borrarPaises(List<Paises> listaPaises) {
        for (int i = 0; i < listaPaises.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaPaises.borrar(listaPaises.get(i));
        }
    }

    @Override
    public void crearPaises(List<Paises> listaPaises) {
        for (int i = 0; i < listaPaises.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaPaises.crear(listaPaises.get(i));
        }
    }

    public List<Paises> consultarPaises() {
        List<Paises> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaPaises.consultarPaises();
        return listMotivosCambiosCargos;
    }

    @Override
    public Paises consultarPais(BigInteger secPaises) {
        Paises subCategoria;
        subCategoria = persistenciaPaises.consultarPais(secPaises);
        return subCategoria;
    }

    @Override
    public BigInteger contarDepartamentosPais(BigInteger secPaises) {
        BigInteger contarDepartamentosPais = null;

        try {
            return contarDepartamentosPais = persistenciaPaises.contarDepartamentosPais(secPaises);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPaises contarDepartamentosPais ERROR : " + e);
            return null;
        }
    }

    @Override
    public BigInteger contarFestivosPais(BigInteger secPaises) {
        BigInteger contarFestivosPais = null;

        try {
            return contarFestivosPais = persistenciaPaises.contarFestivosPais(secPaises);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarPaises contarFestivosPais ERROR : " + e);
            return null;
        }
    }
}
