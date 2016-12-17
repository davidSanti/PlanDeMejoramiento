/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.concesionario.backend.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "Vehiculos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v WHERE v.estado = true")
    , @NamedQuery(name = "Vehiculo.findByVehiculoID", query = "SELECT v FROM Vehiculo v WHERE v.vehiculoID = :vehiculoID")
    , @NamedQuery(name = "Vehiculo.findByMarca", query = "SELECT v FROM Vehiculo v WHERE v.marca = :marca")
    , @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo")
    , @NamedQuery(name = "Vehiculo.findByPrecio", query = "SELECT v FROM Vehiculo v WHERE v.precio = :precio")
    , @NamedQuery(name = "Vehiculo.findByEstado", query = "SELECT v FROM Vehiculo v WHERE v.estado = :estado")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "VehiculoID")
    private Integer vehiculoID;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "Marca")
    private String marca;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Modelo")
    private int modelo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Precio")
    private float precio;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Estado", insertable = false)
    private boolean estado;
    
    @JoinTable(name = "VehiculosConcesionario", joinColumns = {
        @JoinColumn(name = "VehiculoID", referencedColumnName = "VehiculoID")}, inverseJoinColumns = {
        @JoinColumn(name = "NitConcesionario", referencedColumnName = "Nit")})
    @ManyToMany
    private List<Concesionario> concesionarioList;
    
    public Vehiculo() {
    }

    public Vehiculo(Integer vehiculoID) {
        this.vehiculoID = vehiculoID;
    }

    public Vehiculo(Integer vehiculoID, String marca, int modelo, float precio, boolean estado) {
        this.vehiculoID = vehiculoID;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.estado = estado;
    }

    public Integer getVehiculoID() {
        return vehiculoID;
    }

    public void setVehiculoID(Integer vehiculoID) {
        this.vehiculoID = vehiculoID;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Concesionario> getConcesionarioList() {
        return concesionarioList;
    }

    public void setConcesionarioList(List<Concesionario> concesionarioList) {
        this.concesionarioList = concesionarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vehiculoID != null ? vehiculoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.vehiculoID == null && other.vehiculoID != null) || (this.vehiculoID != null && !this.vehiculoID.equals(other.vehiculoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nVehiculoID: " + vehiculoID;
    }
    
}
