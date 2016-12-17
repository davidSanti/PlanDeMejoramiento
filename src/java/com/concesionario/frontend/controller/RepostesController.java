/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.frontend.controller;

import com.concesionario.backend.entities.Vehiculo;
import com.concesionario.backend.entities.Venta;
import com.concesionario.backend.model.VehiculoFacadeLocal;
import com.concesionario.backend.model.VentaFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Santi
 */
@Named(value = "repostesController")
@ViewScoped
public class RepostesController {

    @EJB
    private VehiculoFacadeLocal vehiculoFacadeLocal;
    @EJB
    private VentaFacadeLocal ventaFacadeLocal;

    private Vehiculo vehiculo;
    private Venta venta;
    private List<Vehiculo> vehiculos;
    private float valor;
    
    public RepostesController() {
    }

    @PostConstruct
    public void init() {
        vehiculo = new Vehiculo();
        venta = new Venta();
    }

    public List<Vehiculo> mostrarModeloMasReciente() {
        List<Vehiculo> listaVehiculos = this.vehiculoFacadeLocal.litar();
        List<Vehiculo> vehiculosModeloReciente = new ArrayList();
        vehiculosModeloReciente.add(new Vehiculo(1, "marca", 0, 0, true));

        for (int i = 0; i < listaVehiculos.size(); i++) {

            for (int j = 0; j < vehiculosModeloReciente.size(); j++) {
                if (listaVehiculos.get(i).getModelo() > vehiculosModeloReciente.get(j).getModelo()) {
                    vehiculosModeloReciente.clear();
                    vehiculosModeloReciente.add(listaVehiculos.get(i));

                } else if (listaVehiculos.get(i).getPrecio() == vehiculosModeloReciente.get(j).getPrecio()) {
                    vehiculosModeloReciente.add(listaVehiculos.get(i));
                }

            }
        }

        return vehiculosModeloReciente;
    }

    public List<Vehiculo> mostrarVehiculoMasEconomico() {
        List<Vehiculo> listaVehiculos = this.vehiculoFacadeLocal.litar();
        List<Vehiculo> vehiculosMasEconomicos = new ArrayList();
        vehiculosMasEconomicos.add(new Vehiculo(1, "marca", 0, 500000000, true));

        for (int i = 0; i < listaVehiculos.size(); i++) {

            for (int j = 0; j < vehiculosMasEconomicos.size(); j++) {
                if (listaVehiculos.get(i).getPrecio() < vehiculosMasEconomicos.get(j).getPrecio()) {
                    vehiculosMasEconomicos.clear();
                    vehiculosMasEconomicos.add(listaVehiculos.get(i));
                } else if (listaVehiculos.get(i).getPrecio() == vehiculosMasEconomicos.get(j).getPrecio()) {
                    vehiculosMasEconomicos.add(listaVehiculos.get(i));
                }
            }

        }

        return vehiculosMasEconomicos;
    }

    public List<Vehiculo> mostrarVehiculoMasVendido() {
        List<Venta> ventas = ventaFacadeLocal.findAll();
        List<Vehiculo> vehiculosMasVendidos = new ArrayList();
        int contadorIncial = 0;
        int contadorFinal = 0;

        for (int i = 0; i < ventas.size(); i++) {
            contadorFinal = 0;
            for (int j = i + 1; j < ventas.size(); j++) {
                if (ventas.get(i).getVehiculoID() == ventas.get(j).getVehiculoID()) {
                    contadorFinal += 1;
                }

            }
            if (contadorFinal > contadorIncial) {
                contadorIncial = contadorFinal;
                vehiculosMasVendidos.clear();
                vehiculosMasVendidos.add(ventas.get(i).getVehiculoID());
            } else if (contadorFinal == contadorIncial) {
                vehiculosMasVendidos.add(ventas.get(i).getVehiculoID());
            }
        }

        return vehiculosMasVendidos;
    }

    public List<Vehiculo> mostrarVehiculoValorSuperior(float valor) {
        this.valor = valor;
        List<Vehiculo> listaVehiculos = this.vehiculoFacadeLocal.litar();
        List<Vehiculo> vehiculosValorSuperior = new ArrayList();

        for (int i = 0; i < listaVehiculos.size(); i++) {
            if (listaVehiculos.get(i).getPrecio() > valor) {
                vehiculosValorSuperior.add(listaVehiculos.get(i));
            }

        }
        
        vehiculos = vehiculosValorSuperior;
        return vehiculosValorSuperior;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Vehiculo> getVehiculos() {

         return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

  

}
