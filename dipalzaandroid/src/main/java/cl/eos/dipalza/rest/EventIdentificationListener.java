package cl.eos.dipalza.rest;

public interface EventIdentificationListener {
    enum TheEvent {LOGIN, SELLER, DATA};

    void onEventIdentificaion(TheEvent event, Object data);
}
