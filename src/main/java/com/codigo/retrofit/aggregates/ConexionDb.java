package com.codigo.retrofit.aggregates;

public class ConexionDb {

    //Primero crear una variable private y estatica de la misma clase (SINGLETON)
    private static ConexionDb conexionDb;

    //Segundo Contructor privado para evitar que otros creen instancias (SINGLETON)
    private ConexionDb(){
        System.out.println("Conexion a BD creada!!");
    }

    //Tercero crear metodo publico para obtener la unica instancia (SINGLETON)
    public static ConexionDb obtenerInstancia(){
        if(conexionDb == null){
            conexionDb = new ConexionDb();
        }
        return conexionDb;
    }
}
