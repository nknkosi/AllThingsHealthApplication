package za.co.wethinkcode.allthingshealth.app.db;



import com.google.common.collect.ImmutableList;
import za.co.wethinkcode.allthingshealth.app.db.memory.MemoryDb;
import za.co.wethinkcode.allthingshealth.app.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DataRepository:
 * I define the protocol -- the set of methods -- that other parts of the application can use to
 * persist instances of the domain model (Persons, Expenses, Claims and Settlements).
 * <p>
 * My name, {@code DataRepository}, ins intended to indicate the abstract idea that <em>this</em>
 * is where data lives, without binding to any specific implementation like memory, file or database.
 * <p>
 * For the purposes of our Iteration 2 "WeShare" exercise, a simple, memory-only implementation is
 * provided in {@link MemoryDb}, accessible by calling
 * {@code DataRepository.getInstance()}.
 */
public interface DataRepository {

    DataRepository INSTANCE = new MemoryDb();

    static DataRepository getInstance(){
        return INSTANCE;
    }

    //<editor-fold desc="Persons">
    /**
     * Add a new Person instance to the datastore. If the Person passed to this method already exists
     * in the repository (they have the same ID), we'll return that instance and ignore the request.
     * If you ask for a {@literal null} Person, I will throw a NullPointerException.
     * @param person A non-null Person instance.
     * @return The Person instance you added or that already existed in the db.
     */
    Person addPerson(Person person);

    /**
     * Remove a Person from the database. If you request removal of a Person that is not actually
     * in the database, your request will silently be ignored.
     * @param person A non-null Person instance which will be removed from the db.
     */
    void removePerson(Person person );

    /**
     * Update the given Person instance.
     * @param updatedPerson A non-null Person instance which will be updated.
     */
    void updatePerson(Person updatedPerson );

    /**
     * Find a Person instance with the given email address.
     * @param email Email id of the Person we want to find.
     * @return An Optional containing the Person if they exist in the db, empty otherwise.
     */
    Optional<Person> findPerson( String email );

    /**
     * Answer with a Set of all Person instances in the db.
     * @return A set of Person instances, possible empty, but never {@literal null}.
     */
    ImmutableList<Person> allPersons();
    //</editor-fold>


}