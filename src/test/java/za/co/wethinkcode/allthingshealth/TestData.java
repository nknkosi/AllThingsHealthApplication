package za.co.wethinkcode.allthingshealth;

import za.co.wethinkcode.allthingshealth.app.db.DataRepository;
import za.co.wethinkcode.allthingshealth.app.db.memory.MemoryDb;
import za.co.wethinkcode.allthingshealth.app.model.Person;

public class TestData {
    public static Person me() {
        return new Person("me@mydomain.com");
    }

    public static DataRepository person(){
        DataRepository dataRepository = new MemoryDb();
        dataRepository.addPerson(new Person("nkosi@gmail.com"));
        return dataRepository;
    }
}
