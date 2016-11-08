package com.example.santiagolopezgarcia.subastapp.datos;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 28/10/16.
 */

public interface AdministradorBaseDatosInterface {

    List<String> GetQuerysCreate();

    List<String> GetQuerysUpgrade();

}