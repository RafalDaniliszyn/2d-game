package pl.rafal;

public class Logowanie {
    String name;
    int id;
    boolean status = false;
    OknoLogowania oknoLogowania;

    Logowanie(OknoLogowania oknoLogowania){
        this.name = oknoLogowania.nameGracza;
        this.id = oknoLogowania.idGracza;
        this.status = oknoLogowania.czyZalogowano;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
