package cl.eos.dipalza.rest.responses;

import java.util.List;

public class VendedorListadoResponse {

	List<VendedorListado> data;
	Links links;
	Metadata meta;

	public List<VendedorListado> getData() {
		return data;
	}

	public void setData(List<VendedorListado> data) {
		this.data = data;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}

	public Metadata getMeta() {
		return meta;
	}

	public void setMeta(Metadata meta) {
		this.meta = meta;
	}
	
	

	
	
}
