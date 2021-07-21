package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "SELECT * FROM employee AS e " +
                    "WHERE e.salary > (" +
                    "SELECT salary FROM employee AS ei " +
                    "WHERE ei.id = e.boss_id)",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "SELECT e.id as id , e.name as name , e.salary as salary , e.boss_id as boss_id, e.department_id " +
                    "FROM employee AS e " +
                    "WHERE e.salary IN ( " +
                        "SELECT max(ei.salary) FROM employee AS ei " +
                        "JOIN department ej ON ei.department_id = ej.id " +
                        "GROUP BY ej.id) ",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "SELECT * FROM employee AS e " +
                    "WHERE e.boss_id IS NULL OR e.department_id != " +
                    "(SELECT department_id FROM employee as ei WHERE e.boss_id = ei.id)",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
