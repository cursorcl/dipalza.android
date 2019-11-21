package cl.eos.dipalza.vistas.impl;

import cl.eos.dipalza.ot.OTInicializacion;

public interface IOyenteEventoInicializacion extends IOyente {

	void notificarEventoGrabar(OTInicializacion inicializacion);
}
