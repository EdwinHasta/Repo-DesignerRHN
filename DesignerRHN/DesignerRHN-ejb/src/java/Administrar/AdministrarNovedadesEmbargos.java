/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.DetallesFormasDtos;
import Entidades.EersPrestamos;
import Entidades.EersPrestamosDtos;
import Entidades.Empleados;
import Entidades.FormasDtos;
import Entidades.Juzgados;
import Entidades.MotivosEmbargos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import Entidades.TiposEmbargos;
import Entidades.VWPrestamoDtosRealizados;
import InterfaceAdministrar.AdministrarNovedadesEmbargosInterface;
import InterfacePersistencia.PersistenciaDetallesFormasDtosInterface;
import InterfacePersistencia.PersistenciaEersPrestamosDtosInterface;
import InterfacePersistencia.PersistenciaEersPrestamosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaFormasDtosInterface;
import InterfacePersistencia.PersistenciaJuzgadosInterface;
import InterfacePersistencia.PersistenciaMotivosEmbargosInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTiposEmbargosInterface;
import InterfacePersistencia.PersistenciaVWPrestamoDtosRealizadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarNovedadesEmbargos implements AdministrarNovedadesEmbargosInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaJuzgadosInterface persistenciaJuzgados;
    @EJB
    PersistenciaTiposEmbargosInterface persistenciaTiposEmbargos;
    @EJB
    PersistenciaMotivosEmbargosInterface persistenciaMotivosEmbargos;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaEersPrestamosInterface persistenciaEers;
    @EJB
    PersistenciaEersPrestamosDtosInterface persistenciaEersDtos;
    @EJB
    PersistenciaFormasDtosInterface persistenciaFormasDtos;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaDetallesFormasDtosInterface persistenciaDetallesFormasDtos;
    @EJB
    PersistenciaVWPrestamoDtosRealizadosInterface persistenciaVWPrestamo;
    public EersPrestamosDtos dE;
    public EersPrestamos e;

    @Override
    public List<Empleados> listaEmpleados() {
        return persistenciaEmpleados.empleadosNovedadEmbargo();
    }

    @Override
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.empleadosNovedadEmbargo();
    }

    public List<TiposEmbargos> lovTiposEmbargos() {
        return persistenciaTiposEmbargos.buscarTiposEmbargos();
    }

    public List<Juzgados> lovJuzgados() {
        return persistenciaJuzgados.buscarJuzgados();
    }

    public List<MotivosEmbargos> lovMotivosEmbargos() {
        return persistenciaMotivosEmbargos.buscarMotivosEmbargos();
    }

    public List<Terceros> lovTerceros() {
        return persistenciaTerceros.buscarTerceros();
    }

    public List<Periodicidades> lovPeriodicidades() {
        return persistenciaPeriodicidades.consultarPeriodicidades();
    }

    public List<FormasDtos> lovFormasDtos(BigInteger tipoEmbargo) {
        return persistenciaFormasDtos.formasDescuentos(tipoEmbargo);
    }

    //Segunda Tabla
    public List<EersPrestamos> eersPrestamosEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaEers.eersPrestamosEmpleado(secuenciaEmpleado);
    }

    //LOV Formas de descuento segunda tabla
    public List<DetallesFormasDtos> lovDetallesFormasDescuentos(BigInteger formasDtos) {
        return persistenciaDetallesFormasDtos.detallesFormasDescuentos(formasDtos);
    }

    public List<FormasDtos> formasDescuentos(BigInteger tipoEmbargo) {
        return persistenciaFormasDtos.formasDescuentos(tipoEmbargo);
    }

    //Tercera Tabla
    public List<EersPrestamosDtos> eersPrestamosEmpleadoDtos(BigInteger secuenciaEersPrestamo) {
        return persistenciaEersDtos.eersPrestamosDtosEmpleado(secuenciaEersPrestamo);
    }

    public List<VWPrestamoDtosRealizados> prestamosRealizados(BigInteger secuencia) {
        return persistenciaVWPrestamo.buscarPrestamosDtos(secuencia);
    }

    //AGREGAR, BORRAR Y MODIFICAR DE LA TABLA DE ABAJO //
    @Override
    public void modificarDetalleEmbargo(List<EersPrestamosDtos> listaDetallesEmbargosModificar) {
        for (int i = 0; i < listaDetallesEmbargosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaDetallesEmbargosModificar.get(i).getValor() == null) {
                listaDetallesEmbargosModificar.get(i).setValor(null);
            }
            if (listaDetallesEmbargosModificar.get(i).getPorcentaje() == null) {
                listaDetallesEmbargosModificar.get(i).setPorcentaje(null);
            }
            if (listaDetallesEmbargosModificar.get(i).getSaldoinicial() == null) {
                listaDetallesEmbargosModificar.get(i).setSaldoinicial(null);
            }

            persistenciaEersDtos.editar(dE);
        }
    }

    @Override
    public void borrarDetalleEmbargo(EersPrestamosDtos detallesEmbargos) {
        persistenciaEersDtos.borrar(detallesEmbargos);
    }

    @Override
    public void crearDetalleEmbargo(EersPrestamosDtos detallesEmbargos) {
        persistenciaEersDtos.crear(detallesEmbargos);
    }
    //AGREGAR, BORRAR Y MODIFICAR DE LA TABLA DE Arriba //

    @Override
    public void modificarEmbargo(List<EersPrestamos> listaEmbargosModificar) {
        for (int i = 0; i < listaEmbargosModificar.size(); i++) {
            System.out.println("ListaEmbargosModificar " + listaEmbargosModificar.size());
            System.out.println("Modificando...");
            if (listaEmbargosModificar.get(i).getTipoembargo() == null) {
                listaEmbargosModificar.get(i).setTipoembargo(null);
            }
            if (listaEmbargosModificar.get(i).getCancelaciondocumento() == null) {
                listaEmbargosModificar.get(i).setCancelaciondocumento(null);
            }
            if (listaEmbargosModificar.get(i).getCancelacionfechahasta() == null) {
                listaEmbargosModificar.get(i).setCancelacionfechahasta(null);
            }
            if (listaEmbargosModificar.get(i).getMotivoembargo() == null) {
                listaEmbargosModificar.get(i).setMotivoembargo(null);
            }
            if (listaEmbargosModificar.get(i).getJuzgado() == null) {
                listaEmbargosModificar.get(i).setJuzgado(null);
            }
            if (listaEmbargosModificar.get(i).getNumeroproceso() == null) {
                listaEmbargosModificar.get(i).setNumeroproceso(null);
            }

            persistenciaEers.editar(listaEmbargosModificar.get(i));
        }
    }

    @Override
    public void borrarEmbargo(EersPrestamos embargos) {
        persistenciaEers.borrar(embargos);
    }

    @Override
    public void crearEmbargo(EersPrestamos embargos) {
        persistenciaEers.crear(embargos);
    }
}
