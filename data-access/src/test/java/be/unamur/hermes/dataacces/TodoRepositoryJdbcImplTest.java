package be.unamur.hermes.dataacces;

import be.unamur.hermes.dataaccess.repository.EmployeeRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@TestPropertySource(locations = {"classpath:context_test_h2.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EmployeeRepositoryImpl.class})
public class EmployeeRepositoryImplTest {

    @Autowired
    private EmployeeRepositoryImpl repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findById_should_not_find_anything_because_the_table_is_empty() {
        assertThat(repository.findById(3241L)).isEmpty();
    }

    @Test
    public void create_should_insert_given_todo_and_assign_an_id_automatically() {
        jdbcTemplate.update("INSERT INTO t_author (id, username, password) VALUES (42, 'testauthor', 'pass')");

        repository.create("blabla", 42L);

        List<Todo> todo = repository.findByAuthorId(42L);

        Assertions.assertThat(todo).hasSize(1);
        Todo todo1 = todo.get(0);
        assertThat(todo1.getId()).isPositive();
        assertThat(todo1.getAuthorId()).isEqualTo(42L);
        assertThat(todo1.getContent()).isEqualTo("blabla");
    }

}
