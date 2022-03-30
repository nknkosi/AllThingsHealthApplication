package za.co.wethinkcode.allthingshealth.app.db.memory;

import com.google.common.collect.ImmutableList;
import za.co.wethinkcode.allthingshealth.app.db.DataRepository;
import za.co.wethinkcode.allthingshealth.app.model.Person;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryDb implements DataRepository {
    private final Set<Person> people = new HashSet<>();

    volatile long lastPersonId = 0L;

    public MemoryDb() {
        setupTestData();
    }

    //<editor-fold desc="Persons">

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableList<Person> allPersons() {
        return ImmutableList.copyOf(people);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Person> findPerson(String email) {
        return people.stream()
                .filter(Person -> Person.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person addPerson(Person person) {
        Optional<Person> alreadyExists = findPerson(person.getEmail());
        if (alreadyExists.isPresent()) {
            return alreadyExists.get();
        }
        person.setId(nextPersonId());
        people.add(person);
        return person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePerson(Person person) {
        people.remove(person);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePerson(Person updatedPerson) {
        Optional<Person> PersonOpt = people.stream().filter(Person -> Person.getEmail().equalsIgnoreCase(updatedPerson.getEmail())).findFirst();
        if (PersonOpt.isPresent()) {
            people.remove(PersonOpt.get());
            people.add(updatedPerson);
        }
    }
    //</editor-fold>


    private long nextPersonId() {
        synchronized (this) {
            return ++lastPersonId;
        }
    }
    //</editor-fold>

    //<editor-fold desc="Test Data">
    private void setupTestData() {
        Person nkosi = addPerson(new Person("nkosi@gmail.com"));

    }
    //</editor-fold>
}