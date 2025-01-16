package ru.my.crudApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.my.crudApp.models.Person;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id);
    }
    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO person (name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }
    public void editPerson(int id, Person person){
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id = ?", person.getName(), person.getAge(), person.getEmail(), id);
    }
    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
