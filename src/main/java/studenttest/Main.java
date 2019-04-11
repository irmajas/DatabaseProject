package studenttest;

import studenttest.UserUIService.UIMenu;
import studenttest.databaseService.DBService;

//hibernate singleton
public class Main {

    public static void main(String[] args) {

       DBService.inicializacija();
        UIMenu.letsStart();
    }
}
